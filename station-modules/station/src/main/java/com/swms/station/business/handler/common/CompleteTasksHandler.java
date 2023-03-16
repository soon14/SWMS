package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.EquipmentService;
import com.swms.station.remote.TaskService;
import com.swms.utils.utils.JsonUtils;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.warehouse.constants.WorkStationStatusEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompleteTasksHandler implements IBusinessHandler {

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
        Preconditions.checkState(workStation.getWorkStationStatus() != WorkStationStatusEnum.OFFLINE);
        Preconditions.checkState(CollectionUtils.isNotEmpty(workStation.getOperateTasks()));

        List<Long> taskIds = JsonUtils.string2List(body, Long.class);
        List<Long> allTaskIds = workStation.getOperateTasks().stream().map(OperationTaskDTO::getTaskId).toList();
        Preconditions.checkState(allTaskIds.containsAll(taskIds));

        taskService.completeTasks(taskIds);

        workStation.getOperateTasks().removeIf(operationTaskDTO -> taskIds.contains(operationTaskDTO.getTaskId()));

        workStation.handleUndoContainers(taskService, equipmentService);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.COMPLETE_TASKS;
    }
}
