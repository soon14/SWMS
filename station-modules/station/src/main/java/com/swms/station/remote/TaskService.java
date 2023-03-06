package com.swms.station.remote;

import com.swms.common.utils.JsonUtils;
import com.swms.station.business.model.OperateTask;
import com.swms.wms.api.task.ITaskApi;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @DubboReference
    private ITaskApi iTaskApi;

    public List<OperateTask> queryTasks(String stationCode, List<String> containerCodes) {
        List<Object> objects = iTaskApi.queryTaskList(stationCode, containerCodes);

        if (CollectionUtils.isEmpty(objects)) {
            return null;
        }
        return objects.stream().map(v -> JsonUtils.string2Object(JsonUtils.obj2String(v), OperateTask.class))
            .collect(Collectors.toList());
    }
}
