package com.swms.mdm.main.data.domain.repository;

import com.swms.mdm.main.data.domain.entity.SkuMainData;

import java.util.Collection;
import java.util.List;

public interface SkuMainDataRepository {

    void save(SkuMainData skuMainData);

    void update(SkuMainData skuMainData);

    SkuMainData getSkuMainData(String skuCode, String ownerCode);

    List<SkuMainData> getSkuMainData(Collection<String> skuCodes);

    SkuMainData findById(Long id);
}
