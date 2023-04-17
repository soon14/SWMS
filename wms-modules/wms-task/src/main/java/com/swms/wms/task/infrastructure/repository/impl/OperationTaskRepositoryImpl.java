package com.swms.wms.task.infrastructure.repository.impl;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.repository.OperationTaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperationTaskRepositoryImpl implements OperationTaskRepository {

    @Override
    public void saveAll(List<OperationTask> operationTasks) {

    }

    @Override
    public List<OperationTask> queryContainerTasksByTaskType(String stationCode, List<String> containerCodes, OperationTaskTypeEnum taskType) {
        return null;
    }

    @Override
    public List<OperationTask> findAllByIds(List<Long> taskIds) {
        return null;
    }

    @Override
    public List<OperationTask> findAllByPutWallSlotCode(String putWallSlotCode) {
        return null;
    }
}
