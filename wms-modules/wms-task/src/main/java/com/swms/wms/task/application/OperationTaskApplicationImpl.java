package com.swms.wms.task.application;

import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.ReportAbnormalTaskDTO;
import com.swms.wms.api.task.dto.SplitTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.service.OperationTaskService;
import com.swms.wms.task.domain.transfer.OperationTaskTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationTaskApplicationImpl implements ITaskApi {

    @Autowired
    private OperationTaskService operationTaskService;

    @Autowired
    private OperationTaskTransfer operationTaskTransfer;

    @Override
    public void createOperationTasks(List<OperationTaskDTO> operationTaskDTOS) {
        operationTaskService.createOperationTasks(operationTaskDTOS);
    }

    @Override
    public List<OperationTaskDTO> queryTasks(String stationCode, List<String> containerCodes, OperationTaskTypeEnum taskType) {
        List<OperationTask> operationTasks = operationTaskService.queryContainerTasksByTaskType(stationCode, containerCodes, taskType);
        List<OperationTaskDTO> operationTaskDTOS = operationTaskTransfer.toOperationTaskDTOS(operationTasks);

        //TODO
        // 1. query sku &  sku batch
        return operationTaskDTOS;
    }

    @Override
    public void handleTasks(List<HandleTaskDTO> handleTaskDTOS) {

        //1. handle tasks
        operationTaskService.handleTasks(handleTaskDTOS);

        //2. update stock -> just send event

        //3. update order status -> just send event
    }

    @Override
    public void reportAbnormal(List<ReportAbnormalTaskDTO> reportAbnormalTaskDTOS) {

        //1. update tasks status
        operationTaskService.reportAbnormal(reportAbnormalTaskDTOS);

        //2. create stock abnormal record -> just send event


        //3. reAssign tasks -> just send event

    }

    @Override
    public void splitTasks(List<SplitTaskDTO> splitTaskDTOS) {

        //1. update tasks status

        //2. create new tasks
    }
}
