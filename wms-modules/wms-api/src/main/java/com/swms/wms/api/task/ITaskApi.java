package com.swms.wms.api.task;

import java.util.List;

public interface ITaskApi {

    List<Object> queryTaskList(String stationCode, List<String> containerCodes);
}
