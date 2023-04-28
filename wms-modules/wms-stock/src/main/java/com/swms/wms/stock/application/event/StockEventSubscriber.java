package com.swms.wms.stock.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.repository.ContainerStockTransactionRecordRepository;
import com.swms.wms.stock.domain.service.StockTransferService;
import com.swms.wms.stock.domain.transfer.ContainerStockTransactionRecordTransfer;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.event.StockTransferEvent;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class StockEventSubscriber {

    @Autowired
    private StockTransferService stockTransferService;

    @Autowired
    private ContainerStockRepository containerStockRepository;

    @Autowired
    private ContainerStockTransactionRecordRepository containerStockTransactionRecordRepository;

    @Autowired
    private ContainerStockTransactionRecordTransfer containerStockTransactionRecordTransfer;

    @Subscribe
    public void onEvent(@Valid StockTransferEvent event) {
        event.getStockTransferDTOS().forEach(stockTransferDTO -> {

            //create stock transaction record
            ContainerStockTransactionRecord containerStockTransactionRecord = containerStockTransactionRecordTransfer
                .toDO(stockTransferDTO);
            ContainerStock containerStock = containerStockRepository.findById(stockTransferDTO.getContainerStockId());
            containerStockTransactionRecord.setSourceContainerCode(containerStock.getContainerCode());
            containerStockTransactionRecord.setSourceContainerSlotCode(containerStock.getContainerSlotCode());
            ContainerStockTransactionRecord newRecord = containerStockTransactionRecordRepository.save(containerStockTransactionRecord);

            stockTransferDTO.setContainerStockTransactionRecordId(newRecord.getId());
            if (event.getTaskType() == OperationTaskTypeEnum.PICKING) {
                stockTransferService.transferAndUnlockStock(stockTransferDTO);
            } else {
                stockTransferService.transferStock(stockTransferDTO);
            }
        });
    }
}
