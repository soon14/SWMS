package com.swms.stock.Infrastructure.repository.impl;

import com.swms.stock.domain.entity.SkuBatchStock;
import com.swms.stock.domain.repository.SkuBatchStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuBatchRepositoryImpl implements SkuBatchStockRepository {
    @Override
    public void save(SkuBatchStock skuBatchStock) {

    }

    @Override
    public void saveAll(List<SkuBatchStock> skuBatchStocks) {

    }

    @Override
    public SkuBatchStock findById(Long skuBatchStockId) {
        return null;
    }

    @Override
    public List<SkuBatchStock> findAllByIds(List<Long> skuBatchIds) {
        return null;
    }

    @Override
    public List<SkuBatchStock> findAllBySkuBatchAttributeId(Long skuBatchAttributeId) {
        return null;
    }

    @Override
    public SkuBatchStock findBySkuBatchAttributeIdAndWarehouseAreaCode(Long skuBatchAttributeId, String warehouseAreaCode) {
        return null;
    }
}
