package com.swms.stock.domain.repository;

import com.swms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;

import java.util.List;

public interface SkuBatchStockRepository {

    void save(SkuBatchStock skuBatchStock);

    void saveAll(List<SkuBatchStock> toSkuBatchStocks);

    SkuBatchStock findById(Long skuBatchStockId);

    List<SkuBatchStock> findAllByIds(List<Long> skuBatchIds);

    List<SkuBatchStock> findAllBySkuBatchAttributeId(Long skuBatchAttributeId);

    SkuBatchStock findBySkuBatchAttributeIdAndWarehouseAreaCode(Long skuBatchAttributeId, String warehouseAreaCode);
}
