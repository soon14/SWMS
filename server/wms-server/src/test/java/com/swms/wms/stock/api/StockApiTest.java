package com.swms.wms.stock.api;

import com.swms.wms.BaseTest;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class StockApiTest extends BaseTest {

    @Autowired
    private IStockApi stockApi;

    @Test
    void testSaveContainerStock() {
        StockTransferDTO stockTransferDTO = new StockTransferDTO();
        stockTransferDTO.setTargetContainerCode("containerCode");
        stockTransferDTO.setTargetContainerSlotCode("containerSlotCode");
        stockTransferDTO.setTransferQty(10);
        stockTransferDTO.setSkuBatchAttributeId(1L);
        stockTransferDTO.setTaskId(1L);
        stockTransferDTO.setWarehouseAreaCode("warehouseAreaCode");
        stockApi.createStock(Lists.newArrayList(stockTransferDTO));
    }
}
