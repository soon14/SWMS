package com.swms.wms.api.task;

import com.swms.wms.api.task.dto.OperationTaskDTO;

import java.util.List;

public interface ITaskApi {

    List<OperationTaskDTO> queryTaskList(String stationCode, List<String> containerCodes);
}
