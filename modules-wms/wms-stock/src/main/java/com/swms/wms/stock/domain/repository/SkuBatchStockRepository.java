package com.swms.wms.stock.domain.repository;

import com.swms.wms.stock.domain.entity.SkuBatchStock;

import java.util.Collection;
import java.util.List;

public interface SkuBatchStockRepository {

    SkuBatchStock save(SkuBatchStock skuBatchStock);

    void saveAll(List<SkuBatchStock> toSkuBatchStocks);

    SkuBatchStock findById(Long skuBatchStockId);

    List<SkuBatchStock> findAllByIds(Collection<Long> skuBatchIds);

    List<SkuBatchStock> findAllBySkuBatchAttributeId(Long skuBatchAttributeId);

    List<SkuBatchStock> findAllBySkuBatchAttributeIds(Collection<Long> skuBatchAttributeIds);

    SkuBatchStock findBySkuBatchAttributeIdAndWarehouseAreaId(Long skuBatchAttributeId, Long warehouseAreaId);

}
