package com.swms.wms.api.stock;

import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface IStockApi {

    /**
     * when receiving sku into container, create stock
     *
     * @param stockCreateDTOS
     */
    void createStock(List<StockTransferDTO> stockCreateDTOS);

    void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS);

    void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS);

    /**
     * when stock is move from on area to another area in warehouse, then stock is transferred. e.g: picking, putAway
     * <B>attention: call this function before stock is locked </B>
     *
     * @param stockTransferDTO
     */
    void transferStock(StockTransferDTO stockTransferDTO);
}
