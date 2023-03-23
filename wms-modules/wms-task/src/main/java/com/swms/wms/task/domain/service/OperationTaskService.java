package com.swms.wms.task.domain.service;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.repository.OperationTaskRepository;
import com.swms.wms.task.domain.transfer.OperationTaskTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OperationTaskService {

    @Autowired
    private OperationTaskRepository operationTaskRepository;

    @Autowired
    private OperationTaskTransfer operationTaskTransfer;

    public void createOperationTasks(List<OperationTaskDTO> operationTaskDTOS) {
        List<OperationTask> operationTasks = operationTaskTransfer.toOperationTasks(operationTaskDTOS);
        operationTaskRepository.saveAll(operationTasks);
    }

    public List<OperationTask> queryContainerTasksByTaskType(String stationCode, List<String> containerCodes, OperationTaskTypeEnum taskType) {
        return operationTaskRepository.queryContainerTasksByTaskType(stationCode, containerCodes, taskType);
    }

    public void handleTasks(HandleTaskDTO handleTaskDTO) {

        //1. update operatedQty and status and abnormalQty
        operationTaskRepository.updateTasks(handleTaskDTO);

        //2. create new tasks
        if (handleTaskDTO.getHandleTaskType() == HandleTaskDTO.HandleTaskTypeEnum.SPLIT) {
            List<HandleTaskDTO.HandleTask> splitTasks = handleTaskDTO.getHandleTasks().stream()
                .filter(v -> !Objects.equals(v.getRequiredQty(), v.getOperatedQty()) && v.getOperatedQty() > 0).toList();
            List<Long> taskIds = splitTasks.stream().map(HandleTaskDTO.HandleTask::getTaskId).toList();

            List<OperationTask> operationTasks = operationTaskRepository.findAllByIds(taskIds);
            operationTasks.forEach(operationTask -> {
                operationTask.setId(null);
                operationTask.setRequiredQty(operationTask.getRequiredQty() - operationTask.getOperatedQty());
                operationTask.setAbnormalQty(0);
                operationTask.setTaskNo(null);
                operationTask.setTaskStatus(OperationTaskStatusEnum.CREATED);
            });
            operationTaskRepository.saveAll(operationTasks);
        }
    }

    public List<OperationTask> queryContainerTasksByPutWallSlotCode(String putWallSlotCode) {
        return operationTaskRepository.findAllByPutWallSlotCode(putWallSlotCode);
    }
}
