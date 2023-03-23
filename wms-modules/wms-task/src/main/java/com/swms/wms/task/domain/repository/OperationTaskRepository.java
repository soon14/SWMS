package com.swms.wms.task.domain.repository;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;

import java.util.List;

public interface OperationTaskRepository {

    void saveAll(List<OperationTask> operationTasks);

    List<OperationTask> queryContainerTasksByTaskType(String stationCode, List<String> containerCodes, OperationTaskTypeEnum taskType);

    // There will be some logic to set the status and abnormal qty of the operation
    void updateTasks(HandleTaskDTO handleTaskDTOS);

    List<OperationTask> findAllByIds(List<Long> taskIds);
}
