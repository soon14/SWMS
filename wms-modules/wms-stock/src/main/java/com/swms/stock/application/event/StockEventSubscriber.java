package com.swms.stock.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.stock.domain.repository.ContainerStockRepository;
import com.swms.stock.domain.repository.ContainerStockTransactionRecordRepository;
import com.swms.stock.domain.service.StockTransferService;
import com.swms.stock.domain.transfer.ContainerStockTransactionRecordTransfer;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.event.StockTransferEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
    public void onEvent(StockTransferEvent event) {
        event.getStockTransferDTOS().forEach(stockTransferDTO -> {

            //create stock transaction record
            ContainerStockTransactionRecord containerStockTransactionRecord = containerStockTransactionRecordTransfer
                .toDO(stockTransferDTO);
            ContainerStock containerStock = containerStockRepository.findById(stockTransferDTO.getContainerStockId());
            containerStockTransactionRecord.setSourceContainerCode(containerStock.getContainerCode());
            containerStockTransactionRecord.setSourceContainerSlotCode(containerStock.getContainerSlotCode());
            containerStockTransactionRecordRepository.save(containerStockTransactionRecord);

            stockTransferDTO.setContainerStockTransactionRecordId(containerStockTransactionRecord.getId());
            if (event.getTaskType() == OperationTaskTypeEnum.PICKING) {
                stockTransferService.transferAndUnlockStock(stockTransferDTO);
            } else {
                stockTransferService.transferStock(stockTransferDTO);
            }
        });
    }
}
