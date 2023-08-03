package com.swms.wms.task.domain.service;

import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationTaskService {

    void handleTasks(HandleTaskDTO handleTaskDTO);

    List<OperationTask> getByPutWallSlotAndStation(String putWallSlotCode, @NotNull Long workStationId);

    List<OperationTask> queryOperationTasksByIds(List<Long> taskIds);
}
