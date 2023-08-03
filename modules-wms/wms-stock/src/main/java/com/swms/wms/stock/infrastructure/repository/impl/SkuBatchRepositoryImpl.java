package com.swms.wms.stock.infrastructure.repository.impl;

import com.swms.wms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.stock.domain.repository.SkuBatchStockRepository;
import com.swms.wms.stock.infrastructure.persistence.mapper.SkuBatchStockPORepository;
import com.swms.wms.stock.infrastructure.persistence.po.SkuBatchStockPO;
import com.swms.wms.stock.infrastructure.persistence.transfer.SkuBatchStockPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuBatchRepositoryImpl implements SkuBatchStockRepository {

    @Autowired
    private SkuBatchStockPORepository skuBatchStockPORepository;

    @Autowired
    private SkuBatchStockPOTransfer skuBatchStockPOTransfer;

    @Override
    public SkuBatchStock save(SkuBatchStock skuBatchStock) {
        return skuBatchStockPOTransfer.toDO(skuBatchStockPORepository.save(skuBatchStockPOTransfer.toPO(skuBatchStock)));
    }

    @Override
    public void saveAll(List<SkuBatchStock> skuBatchStocks) {
        skuBatchStockPORepository.saveAll(skuBatchStockPOTransfer.toPOs(skuBatchStocks));
    }

    @Override
    public SkuBatchStock findById(Long skuBatchStockId) {
        SkuBatchStockPO skuBatchStockPO = skuBatchStockPORepository.findById(skuBatchStockId).orElseThrow();
        return skuBatchStockPOTransfer.toDO(skuBatchStockPO);
    }

    @Override
    public List<SkuBatchStock> findAllByIds(List<Long> skuBatchIds) {
        List<SkuBatchStockPO> skuBatchStockPOS = skuBatchStockPORepository.findAllById(skuBatchIds);
        return skuBatchStockPOTransfer.toDOS(skuBatchStockPOS);
    }

    @Override
    public List<SkuBatchStock> findAllBySkuBatchAttributeId(Long skuBatchAttributeId) {
        List<SkuBatchStockPO> skuBatchStocks = skuBatchStockPORepository.findAllBySkuBatchAttributeId(skuBatchAttributeId);
        return skuBatchStockPOTransfer.toDOS(skuBatchStocks);
    }

    @Override
    public SkuBatchStock findBySkuBatchAttributeIdAndWarehouseAreaId(Long skuBatchAttributeId, Long warehouseAreaId) {
        SkuBatchStockPO skuBatchStockPO = skuBatchStockPORepository
            .findBySkuBatchAttributeIdAndWarehouseAreaId(skuBatchAttributeId, warehouseAreaId);
        return skuBatchStockPOTransfer.toDO(skuBatchStockPO);
    }
}
