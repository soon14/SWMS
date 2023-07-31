package com.swms.wms.stock.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.event.StockCreateEvent;
import com.swms.wms.api.task.event.StockTransferEvent;
import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.domain.repository.ContainerStockRepository;
import com.swms.wms.stock.domain.service.StockTransferService;
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

    @Autowired
    private ContainerStockRepository containerStockRepository;

    /**
     * event is available for stock transfer from one container to another.
     * applying scenes like: put away, piking, adjust and so on.
     *
     * @param event
     */
    @Subscribe
    public void onEvent(@Valid StockTransferEvent event) {

        log.info("stock module receive event: " + event.toString());

        ContainerStock containerStock = containerStockRepository.findById(event.getStockTransferDTO().getContainerStockId());
        if (event.getTaskType() == OperationTaskTypeEnum.PICKING) {
            stockTransferService.transferAndUnlockStock(event.getStockTransferDTO(), containerStock);
        } else {
            stockTransferService.transferStock(event.getStockTransferDTO(), containerStock);
        }
    }

    /**
     * event is only available for the first time stock put into the container
     * and the container does not exist the current sku before.
     * Applying Scenes like: receiving
     *
     * @param event
     */
    @Subscribe
    public void onEvent(@Valid StockCreateEvent event) {

        log.info("stock module receive event: " + event.toString());
        stockTransferService.createStock(event.getStockCreateDTOS());

    }
}
