package com.swms.wms.api.task;

import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.ReportAbnormalTaskDTO;
import com.swms.wms.api.task.dto.SplitTaskDTO;

import java.util.List;

public interface ITaskApi {

    void createOperationTasks(List<OperationTaskDTO> operationTaskDTOS);

    List<OperationTaskDTO> queryTaskList(String stationCode, List<String> containerCodes, String taskType);

    void handleTasks(List<HandleTaskDTO> handleTaskDTOS);

    void reportAbnormal(List<ReportAbnormalTaskDTO> reportAbnormalTaskDTOS);

    void splitTasks(List<SplitTaskDTO> splitTaskDTOS);
}
