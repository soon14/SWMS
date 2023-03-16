package com.swms.station.remote;

import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @DubboReference
    private ITaskApi taskApi;

    public List<OperationTaskDTO> queryTasks(String stationCode, List<String> containerCodes) {
        return taskApi.queryTaskList(stationCode, containerCodes);

    }

    public void setTaskApi(ITaskApi iTaskApi) {
        this.taskApi = iTaskApi;
    }

    public void completeTasks(List<Long> taskIds) {

    }
}
