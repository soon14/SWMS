package com.swms.wms.task.infrastructure.repository;

import com.swms.wms.task.domain.entity.OperationTask;

import java.util.List;

public interface OperationTaskRepository {

    void saveAll(List<OperationTask> operationTasks);
}
