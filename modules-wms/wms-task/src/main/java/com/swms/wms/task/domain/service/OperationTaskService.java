package com.swms.wms.task.domain.service;

import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationTaskService {

    List<OperationTask> handleTasks(HandleTaskDTO handleTaskDTO);
}
