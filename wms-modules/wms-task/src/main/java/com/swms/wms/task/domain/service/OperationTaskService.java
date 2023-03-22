package com.swms.wms.task.domain.service;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.ReportAbnormalTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.transfer.OperationTaskTransfer;
import com.swms.wms.task.infrastructure.repository.OperationTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return new ArrayList<>();
    }

    public void handleTasks(List<HandleTaskDTO> handleTaskDTOS) {

    }

    public void reportAbnormal(List<ReportAbnormalTaskDTO> reportAbnormalTaskDTOS) {

    }
}
