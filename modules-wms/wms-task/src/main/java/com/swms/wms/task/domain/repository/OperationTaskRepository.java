package com.swms.wms.task.domain.repository;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.task.domain.entity.OperationTask;

import java.util.List;

public interface OperationTaskRepository {

    void saveAll(List<OperationTask> operationTasks);

    List<OperationTask> queryContainerTasksByTaskType(String stationCode, List<String> containerCodes, OperationTaskTypeEnum taskType);

    List<OperationTask> findAllByIds(List<Long> taskIds);

    List<OperationTask> findAllByPutWallSlotCode(String putWallSlotCode);
}
