package com.swms.wms.task.domain.service;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.ReportAbnormalTaskDTO;
import com.swms.wms.api.task.dto.SplitTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.repository.OperationTaskRepository;
import com.swms.wms.task.domain.transfer.OperationTaskTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void handleTasks(List<HandleTaskDTO> handleTaskDTOS) {
        operationTaskRepository.updateTasks(handleTaskDTOS);
    }

    public void reportAbnormal(List<ReportAbnormalTaskDTO> reportAbnormalTaskDTOS) {
        operationTaskRepository.updateAbnormalQty(reportAbnormalTaskDTOS);
    }

    public void splitTasks(List<SplitTaskDTO> splitTaskDTOS) {
        operationTaskRepository.updateSplitQty(splitTaskDTOS);
    }
}
