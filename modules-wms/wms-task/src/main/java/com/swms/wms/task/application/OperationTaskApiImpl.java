package com.swms.wms.task.application;

import com.swms.domain.event.DomainEventPublisher;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.basic.IPutWallApi;
import com.swms.wms.api.stock.ISkuBatchAttributeApi;
import com.swms.wms.api.stock.dto.SkuBatchAttributeDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.SealContainerDTO;
import com.swms.wms.api.task.event.StockTransferEvent;
import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.entity.TransferContainer;
import com.swms.wms.task.domain.repository.OperationTaskRepository;
import com.swms.wms.task.domain.repository.TransferContainerRepository;
import com.swms.wms.task.domain.service.OperationTaskService;
import com.swms.wms.task.domain.service.TransferContainerService;
import com.swms.wms.task.domain.transfer.OperationTaskTransfer;
import com.swms.wms.task.domain.transfer.TransferContainerTransfer;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class OperationTaskApiImpl implements ITaskApi {

    @Autowired
    private OperationTaskService operationTaskService;

    @Autowired
    private OperationTaskRepository operationTaskRepository;

    @Autowired
    private OperationTaskTransfer operationTaskTransfer;

    @Autowired
    private IPutWallApi putWallApi;

    @Autowired
    private TransferContainerRepository transferContainerRepository;

    @Autowired
    private TransferContainerTransfer transferContainerTransfer;

    @Autowired
    private TransferContainerService transferContainerService;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private ISkuBatchAttributeApi skuBatchAttributeApi;

    @DubboReference
    private ISkuMainDataApi skuMainDataApi;

    @Override
    @EventListener
    public void createOperationTasks(List<OperationTaskDTO> operationTaskDTOS) {
        List<OperationTask> operationTasks = operationTaskTransfer.toOperationTasks(operationTaskDTOS);
        operationTaskRepository.saveAll(operationTasks);
    }

    @Override
    public List<OperationTaskDTO> queryTasks(Long workStationId, List<String> containerCodes, OperationTaskTypeEnum taskType) {
        List<OperationTask> operationTasks = operationTaskRepository.queryContainerTasksByTaskType(workStationId, containerCodes, taskType);

        List<OperationTaskDTO> operationTaskDTOS = operationTaskTransfer.toOperationTaskDTOS(operationTasks);

        Set<Long> skuMainDataIds = operationTaskDTOS.stream()
            .map(OperationTaskDTO::getSkuMainDataId).collect(Collectors.toSet());
        Map<Long, SkuMainDataDTO> skuMainDataDTOMap = skuMainDataApi.getByIds(skuMainDataIds)
            .stream().collect(Collectors.toMap(SkuMainDataDTO::getId, v -> v));

        Set<Long> skuBatchStockIds = operationTaskDTOS.stream().map(OperationTaskDTO::getSkuBatchStockId).collect(Collectors.toSet());
        List<SkuBatchAttributeDTO> skuBatchAttributeDTOS = skuBatchAttributeApi.getBySkuBatchStockIds(skuBatchStockIds);

        operationTaskDTOS.forEach(v -> {
            v.setSkuMainDataDTO(skuMainDataDTOMap.get(v.getSkuMainDataId()));

            SkuBatchAttributeDTO batchAttributeDTO = skuBatchAttributeDTOS.stream()
                .filter(skuBatchAttributeDTO ->
                    skuBatchAttributeDTO.getSkuBatchStockIds().contains(v.getSkuBatchStockId()))
                .findFirst().orElse(null);
            v.setSkuBatchAttributeDTO(batchAttributeDTO);
        });

        return operationTaskDTOS;
    }

    @Override
    public void handleTasks(HandleTaskDTO handleTaskDTO) {

        //1. handle tasks
        operationTaskService.handleTasks(handleTaskDTO);

        //2. update stock
        List<Long> taskIds = handleTaskDTO.getHandleTasks().stream().map(HandleTaskDTO.HandleTask::getTaskId).toList();
        List<OperationTask> operationTasks = operationTaskService.queryOperationTasksByIds(taskIds);
        operationTasks.forEach(v -> {
            StockTransferDTO stockTransferDTO = StockTransferDTO.builder()
                .warehouseCode(v.getWarehouseCode())
                .lockType(v.transferToLockType())
                .containerStockId(v.getContainerStockId())
                .skuBatchStockId(v.getSkuBatchStockId())
                .taskId(v.getId())
                .targetContainerCode(v.getTargetContainerCode())
                .targetContainerSlotCode(v.getTargetContainerSlotCode())
                .transferQty(v.getOperatedQty())
                .warehouseAreaId(v.transferToWarehouseAreaId())
                //TODO need query order no
                .orderNo("TODO")
                .build();
            domainEventPublisher.sendAsyncEvent(StockTransferEvent.builder().stockTransferDTO(stockTransferDTO).taskType(v.getTaskType()).build());
        });

        //3. update order status -> just send event
        domainEventPublisher.sendAsyncEvent(handleTaskDTO);
    }

    @Override
    public void bindContainer(BindContainerDTO bindContainerDTO) {
        //1. set put wall slot transfer container
        putWallApi.bindContainer(bindContainerDTO);
    }

    @Override
    public void sealContainer(SealContainerDTO sealContainerDTO) {

        //1. create transfer container record
        TransferContainer transferContainer = transferContainerTransfer.toDO(sealContainerDTO);
        transferContainerService.setIndexAndTotal(transferContainer);
        transferContainerService.setDestination(transferContainer);

        List<OperationTask> operationTasks = operationTaskService
            .getByPutWallSlotAndStation(sealContainerDTO.getPutWallSlotCode(), sealContainerDTO.getWorkStationId());
        List<TransferContainer.TransferContainerTaskRelation> transferContainerTaskRelations = operationTasks.stream()
            .map(operationTask -> TransferContainer.TransferContainerTaskRelation.builder().transferContainerId(transferContainer.getId())
                .operationTaskId(operationTask.getId()).build()).toList();
        transferContainer.setTransferContainerTasks(transferContainerTaskRelations);
        transferContainerRepository.save(transferContainer);

        //2. callback upstream
    }

}
