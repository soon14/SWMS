package com.swms.wms.task.application;

import com.swms.utils.event.DomainEventPublisher;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.SealContainerDTO;
import com.swms.wms.api.task.event.StockTransferEvent;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.task.domain.aggregate.TransferContainerAggregate;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.service.OperationTaskService;
import com.swms.wms.task.domain.transfer.OperationTaskTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationTaskApplicationImpl implements ITaskApi {

    @Autowired
    private OperationTaskService operationTaskService;

    @Autowired
    private OperationTaskTransfer operationTaskTransfer;

    @Autowired
    private IWorkStationApi iworkStationApi;

    @Autowired
    private TransferContainerAggregate transferContainerAggregate;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Override
    @EventListener
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
    public void handleTasks(HandleTaskDTO handleTaskDTO) {

        //1. handle tasks
        operationTaskService.handleTasks(handleTaskDTO);

        //2. update stock
        List<Long> taskIds = handleTaskDTO.getHandleTasks().stream().map(HandleTaskDTO.HandleTask::getTaskId).toList();
        List<OperationTask> operationTasks = operationTaskService.queryOperationTasksByIds(taskIds);
        List<StockTransferDTO> stockTransferDTOS = operationTasks.stream().map(v -> StockTransferDTO.builder()
            .containerStockId(v.getContainerStockId())
            .lockType(v.transferToLockType())
            .skuBatchStockId(v.getSkuBatchStockId())
            .taskId(v.getId())
            .targetContainerCode(v.getTargetContainerCode())
            .targetContainerSlotCode(v.getTargetContainerSlotCode())
            .transferQty(v.getOperatedQty())
            .warehouseAreaCode(v.transferToWarehouseAreaCode())
            .build()).toList();
        domainEventPublisher.sendAsyncEvent(StockTransferEvent.builder().stockTransferDTOS(stockTransferDTOS).build());

        //3. update order status -> just send event
        domainEventPublisher.sendAsyncEvent(handleTaskDTO);
    }

    @Override
    public void bindContainer(BindContainerDTO bindContainerDTO) {
        //1. set put wall slot transfer container
        iworkStationApi.bindContainer(bindContainerDTO);
    }

    @Override
    public void sealContainer(SealContainerDTO sealContainerDTO) {

        //1. create transfer container record
        transferContainerAggregate.sealContainer(sealContainerDTO);

        //2. notice order module to process status change notification

        //3. callback upstream
    }

}
