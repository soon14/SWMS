package com.swms.wms.task.domain.service;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.ReportAbnormalTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;

import java.util.ArrayList;
import java.util.List;

public class OperationTaskService {

    public void createOperationTasks(List<OperationTaskDTO> operationTaskDTOS) {

    }

    public List<OperationTask> queryContainerTasksByTaskType(List<String> containerCodes, OperationTaskTypeEnum taskType) {
        return new ArrayList<OperationTask>();
    }

    public void handleTasks(List<HandleTaskDTO> handleTaskDTOS) {

    }

    public void reportAbnormal(List<ReportAbnormalTaskDTO> reportAbnormalTaskDTOS) {

    }
}
