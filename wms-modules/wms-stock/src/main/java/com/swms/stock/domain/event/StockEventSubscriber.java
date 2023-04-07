package com.swms.stock.domain.event;

import com.google.common.eventbus.Subscribe;
import com.swms.stock.domain.aggregate.StockAggregate;
import com.swms.stock.domain.entity.ContainerStock;
import com.swms.stock.domain.entity.ContainerStockTransactionRecord;
import com.swms.stock.domain.service.StockManagement;
import com.swms.stock.domain.service.StockQuery;
import com.swms.stock.domain.transfer.ContainerStockTransactionRecordTransfer;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.event.StockTransferEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockEventSubscriber {

    @Autowired
    private StockAggregate stockAggregate;

    @Autowired
    private StockManagement stockManagement;

    @Autowired
    private StockQuery stockQuery;

    @Autowired
    private ContainerStockTransactionRecordTransfer containerStockTransactionRecordTransfer;

    @Subscribe
    public void onEvent(StockTransferEvent event) {
        event.getStockTransferDTOS().forEach(stockTransferDTO -> {

            //create stock transaction record
            ContainerStockTransactionRecord containerStockTransactionRecord = containerStockTransactionRecordTransfer
                .toContainerStockTransactionRecord(stockTransferDTO);
            ContainerStock containerStock = stockQuery.queryByContainerStockId(stockTransferDTO.getContainerStockId());
            containerStockTransactionRecord.setSourceContainerCode(containerStock.getContainerCode());
            containerStockTransactionRecord.setSourceContainerSlotCode(containerStock.getContainerSlotCode());
            stockManagement.saveContainerStockTransactionRecord(containerStockTransactionRecord);

            stockTransferDTO.setContainerStockTransactionRecordId(containerStockTransactionRecord.getId());
            if (event.getTaskType() == OperationTaskTypeEnum.PICKING) {
                stockAggregate.transferAndUnlockStock(stockTransferDTO);
            } else {
                stockAggregate.transferStock(stockTransferDTO);
            }
        });
    }
}
