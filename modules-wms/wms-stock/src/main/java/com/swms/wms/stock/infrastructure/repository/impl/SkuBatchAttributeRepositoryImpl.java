package com.swms.wms.stock.infrastructure.repository.impl;

import com.swms.wms.stock.domain.entity.SkuBatchAttribute;
import com.swms.wms.stock.domain.repository.SkuBatchAttributeRepository;
import com.swms.wms.stock.infrastructure.persistence.mapper.SkuBatchAttributePORepository;
import com.swms.wms.stock.infrastructure.persistence.transfer.SkuBatchAttributePOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SkuBatchAttributeRepositoryImpl implements SkuBatchAttributeRepository {

    @Autowired
    private SkuBatchAttributePORepository skuBatchAttributePORepository;

    @Autowired
    private SkuBatchAttributePOTransfer skuBatchAttributePOTransfer;

    @Override
    public SkuBatchAttribute save(SkuBatchAttribute skuBatchAttribute) {
        return skuBatchAttributePOTransfer.toDO(skuBatchAttributePORepository.save(skuBatchAttributePOTransfer.toPO(skuBatchAttribute)));
    }

    @Override
    public List<SkuBatchAttribute> findAllBySkuId(Long skuId) {
        return skuBatchAttributePOTransfer.toDOs(skuBatchAttributePORepository.findAllBySkuId(skuId));
    }

    @Override
    public List<SkuBatchAttribute> findAllByIds(Collection<Long> skuBatchAttributeIds) {
        return skuBatchAttributePOTransfer.toDOs(skuBatchAttributePORepository.findAllById(skuBatchAttributeIds));
    }

    @Override
    public SkuBatchAttribute findBySkuIdAndBatchNo(Long skuId, String batchNo) {
        return skuBatchAttributePOTransfer.toDO(skuBatchAttributePORepository.findBySkuIdAndBatchNo(skuId, batchNo));
    }

    @Override
    public List<SkuBatchAttribute> findAllBySkuIds(Collection<Long> skuIds) {
        return skuBatchAttributePOTransfer.toDOs(skuBatchAttributePORepository.findAllBySkuIdIn(skuIds));
    }
}
