package com.swms.wms.task.api;

import com.google.common.collect.Lists;
import com.swms.tenant.config.util.TenantContext;
import com.swms.utils.utils.ObjectUtils;
import com.swms.wms.BaseTest;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class TaskApiTest extends BaseTest {

    @Autowired
    private ITaskApi taskApi;

    @Test
    void testFullFlow() {

        OperationTaskDTO operationTaskDTO = ObjectUtils.getRandomObject(OperationTaskDTO.class);
        operationTaskDTO.setRequiredQty(10);
        operationTaskDTO.setOperatedQty(0);
        operationTaskDTO.setTaskType(OperationTaskTypeEnum.PICKING);
        operationTaskDTO.setTaskStatus(null);
        taskApi.createOperationTasks(Lists.newArrayList(operationTaskDTO));

        List<OperationTaskDTO> operationTaskDTOS = taskApi.queryTasks(operationTaskDTO.getStationCode(),
            Lists.newArrayList(operationTaskDTO.getSourceContainerCode()), operationTaskDTO.getTaskType());
        Assertions.assertEquals(1, operationTaskDTOS.size());

        HandleTaskDTO.HandleTask handleTask = HandleTaskDTO.HandleTask.builder().taskId(operationTaskDTOS.get(0).getId())
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.COMPLETE)
            .requiredQty(operationTaskDTOS.get(0).getRequiredQty()).operatedQty(10).abnormalQty(0).build();
        HandleTaskDTO handleTaskDTO = HandleTaskDTO.builder().handleTasks(Lists.newArrayList(handleTask))
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.COMPLETE).build();
        taskApi.handleTasks(handleTaskDTO);

        operationTaskDTOS = taskApi.queryTasks(operationTaskDTO.getStationCode(),
            Lists.newArrayList(operationTaskDTO.getSourceContainerCode()), operationTaskDTO.getTaskType());
        Assertions.assertSame(OperationTaskStatusEnum.PROCESSED, operationTaskDTOS.get(0).getTaskStatus());
    }

    @Test
    void testMultipleTenant() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            final int count = i;
            new Thread(() -> {
                TenantContext.setCurrentTenant("test" + count);
                testFullFlow();
            }).start();
        }
    }
}
