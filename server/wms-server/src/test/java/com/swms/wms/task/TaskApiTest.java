package com.swms.wms.task;

import com.google.common.collect.Lists;
import com.swms.common.utils.utils.ObjectUtils;
import com.swms.common.utils.utils.TenantContext;
import com.swms.wms.BaseTest;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.repository.OperationTaskRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

class TaskApiTest extends BaseTest {

    @Autowired
    private ITaskApi taskApi;

    @Autowired
    private IStockApi stockApi;

    @Autowired
    private OperationTaskRepository operationTaskRepository;

    @Test
    @Transactional
    void testFullFlow() {

        createContainerStock();
        List<ContainerStockDTO> containerStocks = stockApi.getContainerStock(containerCode);

        Assertions.assertTrue(CollectionUtils.isNotEmpty(containerStocks));
        ContainerStockDTO containerStockDTO = containerStocks.get(0);

        OperationTaskDTO operationTaskDTO = ObjectUtils.getRandomObject(OperationTaskDTO.class);
        operationTaskDTO.setRequiredQty(10);
        operationTaskDTO.setOperatedQty(0);
        operationTaskDTO.setTaskType(OperationTaskTypeEnum.PICKING);
        operationTaskDTO.setTaskStatus(null);
        operationTaskDTO.setContainerStockId(containerStockDTO.getId());
        operationTaskDTO.setSourceContainerCode(containerStockDTO.getContainerCode());
        operationTaskDTO.setSkuBatchStockId(containerStockDTO.getSkuBatchStockId());
        operationTaskDTO.setSourceContainerSlot(containerStockDTO.getContainerSlotCode());
        taskApi.createOperationTasks(Lists.newArrayList(operationTaskDTO));

        List<OperationTaskDTO> operationTaskDTOS = taskApi.queryTasks(operationTaskDTO.getWorkStationId(),
            Lists.newArrayList(operationTaskDTO.getSourceContainerCode()), operationTaskDTO.getTaskType());
        Assertions.assertEquals(1, operationTaskDTOS.size());

        ContainerStockLockDTO containerStockLockDTO = ContainerStockLockDTO.builder()
            .containerStockId(containerStockDTO.getId()).lockQty(10).lockType(StockLockTypeEnum.OUTBOUND)
            .taskId(operationTaskDTOS.get(0).getId()).build();
        stockApi.lockContainerStock(Lists.newArrayList(containerStockLockDTO));

        HandleTaskDTO.HandleTask handleTask = HandleTaskDTO.HandleTask.builder().taskId(operationTaskDTOS.get(0).getId())
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.COMPLETE)
            .requiredQty(operationTaskDTOS.get(0).getRequiredQty()).operatedQty(10).abnormalQty(0).build();
        HandleTaskDTO handleTaskDTO = HandleTaskDTO.builder().handleTasks(Lists.newArrayList(handleTask))
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.COMPLETE).build();
        taskApi.handleTasks(handleTaskDTO);

        operationTaskDTOS = taskApi.queryTasks(operationTaskDTO.getWorkStationId(),
            Lists.newArrayList(operationTaskDTO.getSourceContainerCode()), operationTaskDTO.getTaskType());
        Assertions.assertSame(OperationTaskStatusEnum.PROCESSED, operationTaskDTOS.get(0).getTaskStatus());
    }

    @Test
    void testHandleTask() throws InterruptedException {
        List<OperationTask> operationTasks = operationTaskRepository.findAllByIds(Lists.newArrayList(492351075085586432L));

        HandleTaskDTO.HandleTask handleTask = HandleTaskDTO.HandleTask.builder().taskId(operationTasks.get(0).getId())
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.COMPLETE)
            .requiredQty(operationTasks.get(0).getRequiredQty()).operatedQty(3).abnormalQty(0).build();
        HandleTaskDTO handleTaskDTO = HandleTaskDTO.builder().handleTasks(Lists.newArrayList(handleTask))
            .handleTaskType(HandleTaskDTO.HandleTaskTypeEnum.COMPLETE).build();
        taskApi.handleTasks(handleTaskDTO);

        Thread.sleep(10000L);
    }

    @Test
    void testMultipleTenant() {
        for (int i = 0; i < 2; i++) {
            final int count = i;
            new Thread(() -> {
                TenantContext.setCurrentTenant("test" + count);
                testFullFlow();
            }).start();
        }
    }
}
