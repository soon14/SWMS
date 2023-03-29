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

    void unLockStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS);

    void lockSkuBatchStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS);

    void lockContainerStock(List<ContainerStockLockDTO> containerStockLockDTOS);

    /**
     * stock transfer from one container to another , and from one area to another
     *
     * @param stockTransferDTO
     */
    void transferStock(StockTransferDTO stockTransferDTO);
}
