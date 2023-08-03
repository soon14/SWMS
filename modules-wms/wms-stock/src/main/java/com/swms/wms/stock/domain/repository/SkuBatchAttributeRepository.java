package com.swms.wms.stock.domain.repository;

import com.swms.wms.stock.domain.entity.SkuBatchAttribute;

import java.util.Collection;
import java.util.List;

public interface SkuBatchAttributeRepository {

    void save(SkuBatchAttribute skuBatchAttribute);

    List<SkuBatchAttribute> findAllBySkuId(Long skuId);

    List<SkuBatchAttribute> findAllByIds(Collection<Long> skuBatchAttributeIds);
}
