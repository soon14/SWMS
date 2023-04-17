package com.swms.stock.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swms.stock.infrastructure.persistence.mapper.SkuBatchStockMapper;
import com.swms.stock.infrastructure.persistence.po.SkuBatchStockPO;
import com.swms.stock.infrastructure.persistence.transfer.SkuBatchStockPOTransfer;
import com.swms.stock.domain.entity.SkuBatchStock;
import com.swms.stock.domain.repository.SkuBatchStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuBatchRepositoryImpl implements SkuBatchStockRepository {

    @Autowired
    private SkuBatchStockMapper skuBatchStockMapper;

    @Autowired
    private SkuBatchStockPOTransfer skuBatchStockTransfer;

    @Override
    public void save(SkuBatchStock skuBatchStock) {
        skuBatchStockMapper.insert(skuBatchStockTransfer.toSkuBatchStockPO(skuBatchStock));
    }

    @Override
    public void saveAll(List<SkuBatchStock> skuBatchStocks) {

    }

    @Override
    public SkuBatchStock findById(Long skuBatchStockId) {
        SkuBatchStockPO skuBatchStockPO = skuBatchStockMapper.selectById(skuBatchStockId);
        return skuBatchStockTransfer.toSkuBatchStock(skuBatchStockPO);
    }

    @Override
    public List<SkuBatchStock> findAllByIds(List<Long> skuBatchIds) {
        List<SkuBatchStockPO> skuBatchStockPOS = skuBatchStockMapper.selectBatchIds(skuBatchIds);
        return skuBatchStockTransfer.toSkuBatchStocks(skuBatchStockPOS);
    }

    @Override
    public List<SkuBatchStock> findAllBySkuBatchAttributeId(Long skuBatchAttributeId) {
        LambdaQueryWrapper<SkuBatchStockPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuBatchStockPO::getSkuBatchAttributeId, skuBatchAttributeId);
        List<SkuBatchStockPO> skuBatchStocks = skuBatchStockMapper.selectList(wrapper);
        return skuBatchStockTransfer.toSkuBatchStocks(skuBatchStocks);
    }

    @Override
    public SkuBatchStock findBySkuBatchAttributeIdAndWarehouseAreaCode(Long skuBatchAttributeId, String warehouseAreaCode) {
        LambdaQueryWrapper<SkuBatchStockPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuBatchStockPO::getSkuBatchAttributeId, skuBatchAttributeId);
        wrapper.eq(SkuBatchStockPO::getWarehouseAreaCode, warehouseAreaCode);
        SkuBatchStockPO skuBatchStockPO = skuBatchStockMapper.selectOne(wrapper);
        return skuBatchStockTransfer.toSkuBatchStock(skuBatchStockPO);
    }
}
