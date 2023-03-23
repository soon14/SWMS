package com.swms.wms.api.task;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;

import java.util.List;

public interface ITaskApi {

    void createOperationTasks(List<OperationTaskDTO> operationTaskDTOS);

    List<OperationTaskDTO> queryTasks(String stationCode, List<String> containerCodes, OperationTaskTypeEnum taskType);

    void handleTasks(HandleTaskDTO handleTaskDTO);

}
