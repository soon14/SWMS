package com.swms.wms.task.domain.repository;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.ReportAbnormalTaskDTO;
import com.swms.wms.api.task.dto.SplitTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;

import java.util.List;

public interface OperationTaskRepository {

    void saveAll(List<OperationTask> operationTasks);

    List<OperationTask> queryContainerTasksByTaskType(String stationCode, List<String> containerCodes, OperationTaskTypeEnum taskType);

    void updateTasks(List<HandleTaskDTO> handleTaskDTOS);

    void updateAbnormalQty(List<ReportAbnormalTaskDTO> reportAbnormalTaskDTOS);

    void updateSplitQty(List<SplitTaskDTO> splitTaskDTOS);

}
