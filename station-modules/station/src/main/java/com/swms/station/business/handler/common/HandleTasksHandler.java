package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.handler.event.HandleTasksEvent;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.EquipmentService;
import com.swms.station.remote.TaskService;
import com.swms.utils.utils.JsonUtils;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HandleTasksHandler implements IBusinessHandler {

    @Autowired
    private WorkStationManagement workStationManagement;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EquipmentService equipmentService;

    @Override
    public void execute(String body, String stationCode) {
        WorkStation workStation = workStationManagement.getWorkStation(stationCode);
        Preconditions.checkState(workStation != null);
        Preconditions.checkState(body != null);
        Preconditions.checkState(workStation.getWorkStationStatus() != WorkStationStatusEnum.OFFLINE);
        Preconditions.checkState(CollectionUtils.isNotEmpty(workStation.getOperateTasks()));

        HandleTasksEvent handleTasksEvent = JsonUtils.string2Object(body, HandleTasksEvent.class);
        Preconditions.checkState(handleTasksEvent != null);

        List<OperationTaskDTO> operateTasks = workStation.getOperateTasks().stream()
            .filter(operationTaskDTO -> handleTasksEvent.getTaskIds().contains(operationTaskDTO.getId()))
            // calculate operated qty order by required qty descending
            .sorted(Comparator.comparing(OperationTaskDTO::getOperatedQty)).toList();
        Preconditions.checkState(operateTasks.size() == handleTasksEvent.getTaskIds().size());

        if (handleTasksEvent.getHandleTaskType() == HandleTaskDTO.HandleTaskTypeEnum.COMPLETE) {
            Integer total = operateTasks.stream().map(OperationTaskDTO::getOperatedQty).reduce(0, Integer::sum);
            Preconditions.checkState(!Objects.equals(total, handleTasksEvent.getOperatedQty()));
        }

        final AtomicInteger totalOperatedQty = new AtomicInteger(handleTasksEvent.getOperatedQty());
        List<HandleTaskDTO.HandleTask> handleTasks = operateTasks.stream().map(operationTaskDTO -> {
            int operatedQty = Math.min(operationTaskDTO.getRequiredQty(), totalOperatedQty.get());
            totalOperatedQty.set(Math.max(0, totalOperatedQty.get() - operationTaskDTO.getRequiredQty()));

            HandleTaskDTO.HandleTask handleTask = HandleTaskDTO.HandleTask.builder()
                .taskId(operationTaskDTO.getId())
                .handleTaskType(handleTasksEvent.getHandleTaskType())
                .operatedQty(operatedQty)
                .requiredQty(operationTaskDTO.getRequiredQty())
                .taskType(workStation.getOperationTaskType())
                .build();
            handleTask.setAbnormalQty();
            handleTask.setTaskStatus();
            return handleTask;
        }).toList();

        taskService.handleTasks(HandleTaskDTO.builder().handleTaskType(handleTasksEvent.getHandleTaskType()).handleTasks(handleTasks).build());
        workStation.removeOperateTasks(handleTasks.stream().map(HandleTaskDTO.HandleTask::getTaskId).toList());
        workStation.handleUndoContainers(taskService, equipmentService);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.HANDLE_TASKS;
    }
}
