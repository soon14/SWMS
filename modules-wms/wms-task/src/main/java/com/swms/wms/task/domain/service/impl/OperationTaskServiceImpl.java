package com.swms.wms.task.domain.service.impl;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.repository.OperationTaskRepository;
import com.swms.wms.task.domain.service.OperationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OperationTaskServiceImpl implements OperationTaskService {

    @Autowired
    private OperationTaskRepository operationTaskRepository;

    @Transactional
    public List<OperationTask> handleTasks(HandleTaskDTO handleTaskDTO) {

        //1. update operatedQty and status and abnormalQty
        Map<Long, HandleTaskDTO.HandleTask> handleTaskMap = handleTaskDTO.getHandleTasks().stream()
            .collect(Collectors.toMap(HandleTaskDTO.HandleTask::getTaskId, v -> v));
        List<OperationTask> operationTasks = operationTaskRepository.findAllByIds(handleTaskDTO.getHandleTasks()
            .stream().map(HandleTaskDTO.HandleTask::getTaskId).toList());
        operationTasks.forEach(operationTask -> {
            HandleTaskDTO.HandleTask handleTask = handleTaskMap.get(operationTask.getId());
            operationTask.operate(handleTask.getOperatedQty(), handleTask.getAbnormalQty());
        });
        operationTaskRepository.saveAll(operationTasks);

        //2. create new tasks
        if (handleTaskDTO.getHandleTaskType() == HandleTaskDTO.HandleTaskTypeEnum.SPLIT) {
            List<OperationTask> splitTasks = operationTasks.stream()
                .filter(v -> !Objects.equals(v.getRequiredQty(), v.getOperatedQty()) && v.getOperatedQty() > 0).toList();
            splitTasks.forEach(operationTask -> {
                operationTask.setId(null);
                operationTask.setRequiredQty(operationTask.getRequiredQty() - operationTask.getOperatedQty());
                operationTask.setAbnormalQty(0);
                operationTask.setTaskStatus(OperationTaskStatusEnum.CREATED);
            });
            operationTaskRepository.saveAll(splitTasks);
        }

        return operationTasks;
    }

}
