package com.swms.wms.stock.api;

import com.swms.wms.BaseTest;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.event.StockTransferEvent;
import com.swms.wms.stock.application.event.StockEventSubscriber;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class StockApiTest extends BaseTest {

    @Autowired
    private IStockApi stockApi;

    @Autowired
    private StockEventSubscriber stockEventSubscriber;

    @Test
    void testSaveContainerStock() {
        StockTransferDTO stockTransferDTO = new StockTransferDTO();
        stockTransferDTO.setTargetContainerCode("containerCode");
        stockTransferDTO.setTargetContainerSlotCode("containerSlotCode");
        stockTransferDTO.setTransferQty(10);
        stockTransferDTO.setSkuBatchAttributeId(1L);
        stockTransferDTO.setTaskId(1L);
        stockTransferDTO.setSkuId(1L);
        stockTransferDTO.setWarehouseAreaCode("warehouseAreaCode");
        stockApi.createStock(Lists.newArrayList(stockTransferDTO));

        List<ContainerStockDTO> containerStocks = stockApi.getContainerStock("containerCode");
        Assertions.assertEquals(1, containerStocks.size());
    }

    @Test
    void testTransfer() {

        List<ContainerStockDTO> containerStocks = stockApi.getContainerStock("containerCode");
        ContainerStockDTO containerStockDTO = containerStocks.get(0);

        StockTransferDTO stockTransferDTO = new StockTransferDTO();
        stockTransferDTO.setContainerStockId(containerStockDTO.getId());
        stockTransferDTO.setTargetContainerCode("targetContainerCode");
        stockTransferDTO.setTargetContainerSlotCode("A");
        stockTransferDTO.setTransferQty(10);
        stockTransferDTO.setSkuBatchAttributeId(containerStockDTO.getSkuBatchAttributeId());
        stockTransferDTO.setTaskId(1L);
        stockTransferDTO.setSkuId(1L);
        stockTransferDTO.setSkuBatchStockId(439838141709422592L);
        stockTransferDTO.setWarehouseAreaCode("B");
        stockTransferDTO.setOrderNo("orderNo");
        stockEventSubscriber.onEvent(StockTransferEvent.builder().stockTransferDTOS(Lists.newArrayList(stockTransferDTO)).taskType(OperationTaskTypeEnum.ONE_STEP_RELOCATION).build());
        containerStocks = stockApi.getContainerStock("containerCode");
        Assertions.assertEquals(0, (int) containerStocks.get(0).getAvailableQty());

    }
}
