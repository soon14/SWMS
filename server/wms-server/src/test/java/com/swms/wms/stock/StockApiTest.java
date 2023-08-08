package com.swms.wms.stock;

import com.swms.wms.BaseTest;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.event.StockCreateEvent;
import com.swms.wms.api.task.event.StockTransferEvent;
import com.swms.wms.stock.application.event.StockEventSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

class StockApiTest extends BaseTest {

    @Autowired
    private IStockApi stockApi;

    @Autowired
    private StockEventSubscriber stockEventSubscriber;

    @Test
    void testSaveContainerStock() {
        String containerCode = UUID.randomUUID().toString();
        StockCreateDTO stockTransferDTO = new StockCreateDTO();
        stockTransferDTO.setTargetContainerCode(containerCode);
        stockTransferDTO.setTargetContainerSlotCode("containerSlotCode");
        stockTransferDTO.setSourceContainerCode("lpn");
        stockTransferDTO.setTransferQty(10);
        stockTransferDTO.setSkuBatchAttributeId(1L);
        stockTransferDTO.setSkuId(1L);
        stockTransferDTO.setOrderNo("ABC_ORDER");
        stockTransferDTO.setWarehouseAreaId(1L);
        stockTransferDTO.setWarehouseCode("ABC");

        stockEventSubscriber.onEvent(StockCreateEvent.builder().stockCreateDTO(stockTransferDTO).build());

        List<ContainerStockDTO> containerStocks = stockApi.getContainerStock(containerCode);
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
        stockTransferDTO.setTaskId(1L);
        stockTransferDTO.setSkuId(1L);
        stockTransferDTO.setSkuBatchStockId(473803572212011008L);
        stockTransferDTO.setWarehouseAreaId(2L);
        stockTransferDTO.setOrderNo("orderNo");
        stockTransferDTO.setWarehouseCode("ABC");
        stockEventSubscriber.onEvent(StockTransferEvent.builder().stockTransferDTO(stockTransferDTO).taskType(OperationTaskTypeEnum.ONE_STEP_RELOCATION).build());
        containerStocks = stockApi.getContainerStock("containerCode");
        Assertions.assertEquals(0, (int) containerStocks.get(0).getAvailableQty());

    }
}
