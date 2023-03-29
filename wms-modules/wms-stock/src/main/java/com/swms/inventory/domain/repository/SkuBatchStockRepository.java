package com.swms.inventory.domain.repository;

import com.swms.inventory.domain.entity.SkuBatchStock;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface SkuBatchStockRepository {

    void saveAll(List<SkuBatchStock> toSkuBatchStocks);

    void lockStock(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS);

    void subtractStock(StockTransferDTO stockDeductDTOS);

    void addStock(List<StockTransferDTO> stockCreateDTOS);

    List<SkuBatchStock> findAllByIds(List<Long> skuBatchIds);

    List<SkuBatchStock> findAllBySkuBatchAttributeId(Long skuBatchAttributeId);

    SkuBatchStock findBySkuBatchAttributeIdAndWarehouseAreaCode(Long skuBatchAttributeId, String warehouseAreaCode);
}
