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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@Slf4j
public class StockEventSubscriber {

    @Autowired
    private StockTransferService stockTransferService;

    @Subscribe
    public void onEvent(@Valid StockTransferEvent event) {

        log.info("stock module receive event: " + event.toString());

        if (event.getTaskType() == OperationTaskTypeEnum.PICKING) {
            stockTransferService.transferAndUnlockStock(event.getStockTransferDTO());
        } else {
            stockTransferService.transferStock(event.getStockTransferDTO());
        }
    }
}
