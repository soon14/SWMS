package com.swms.stock.domain.event;

import com.google.common.eventbus.Subscribe;
import com.swms.stock.domain.aggregate.StockAggregate;
import com.swms.wms.api.task.event.StockTransferEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockEventSubscriber {

    @Autowired
    private StockAggregate stockAggregate;

    @Subscribe
    public void onEvent(StockTransferEvent event) {
        event.getStockTransferDTOS().forEach(stockTransferDTO -> {
            stockAggregate.transferStock(stockTransferDTO);
        });
    }
}
