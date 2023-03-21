package com.swms.wms.task.application;

import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.ReportAbnormalTaskDTO;
import com.swms.wms.api.task.dto.SplitTaskDTO;
import com.swms.wms.task.domain.service.OperationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationTaskApplicationImpl implements ITaskApi {

    @Autowired
    private OperationTaskService operationTaskService;


    @Override
    public void createOperationTasks(List<OperationTaskDTO> operationTaskDTOS) {

    }

    @Override
    public List<OperationTaskDTO> queryTaskList(String stationCode, List<String> containerCodes, String taskType) {

        return null;
    }

    @Override
    public void handleTasks(List<HandleTaskDTO> handleTaskDTOS) {

    }

    @Override
    public void reportAbnormal(List<ReportAbnormalTaskDTO> reportAbnormalTaskDTOS) {

    }

    @Override
    public void splitTasks(List<SplitTaskDTO> splitTaskDTOS) {

    }
}
