package com.swms.wms.stock.infrastructure.repository.impl;

import com.swms.wms.stock.domain.entity.SkuBatchAttribute;
import com.swms.wms.stock.domain.repository.SkuBatchAttributeRepository;
import com.swms.wms.stock.infrastructure.persistence.mapper.SkuBatchAttributePORepository;
import com.swms.wms.stock.infrastructure.persistence.transfer.SkuBatchAttributePOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuBatchAttributeRepositoryImpl implements SkuBatchAttributeRepository {

    @Autowired
    private SkuBatchAttributePORepository skuBatchAttributePORepository;

    @Autowired
    private SkuBatchAttributePOTransfer skuBatchAttributePOTransfer;

    @Override
    public void save(SkuBatchAttribute skuBatchAttribute) {
        skuBatchAttributePORepository.save(skuBatchAttributePOTransfer.toPO(skuBatchAttribute));
    }

    @Override
    public List<SkuBatchAttribute> findAllBySkuId(Long skuId) {
        return skuBatchAttributePOTransfer.toDOs(skuBatchAttributePORepository.findAllBySkuId(skuId));
    }
}
