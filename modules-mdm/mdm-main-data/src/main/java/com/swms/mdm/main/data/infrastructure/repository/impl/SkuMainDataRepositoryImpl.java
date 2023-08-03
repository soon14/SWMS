package com.swms.mdm.main.data.infrastructure.repository.impl;

import com.swms.mdm.main.data.domain.entity.SkuMainData;
import com.swms.mdm.main.data.domain.repository.SkuMainDataRepository;
import com.swms.mdm.main.data.infrastructure.persistence.mapper.SkuMainDataPORepository;
import com.swms.mdm.main.data.infrastructure.persistence.transfer.SkuMainDataPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SkuMainDataRepositoryImpl implements SkuMainDataRepository {

    @Autowired
    private SkuMainDataPORepository skuMainDataPORepository;

    @Autowired
    private SkuMainDataPOTransfer skuMainDataPOTransfer;

    @Override
    public void save(SkuMainData skuMainData) {
        skuMainDataPORepository.save(skuMainDataPOTransfer.toPO(skuMainData));
    }

    @Override
    public void update(SkuMainData skuMainData) {
        skuMainDataPORepository.save(skuMainDataPOTransfer.toPO(skuMainData));
    }

    @Override
    public SkuMainData getSkuMainData(String skuCode, String ownerCode) {
        return skuMainDataPOTransfer.toDO(skuMainDataPORepository.findBySkuCodeAndOwnerCode(skuCode, ownerCode));
    }

    @Override
    public List<SkuMainData> getSkuMainData(Collection<String> skuCodes) {
        return skuMainDataPOTransfer.toDOS(skuMainDataPORepository.findAllBySkuCodeIn(skuCodes));
    }

    @Override
    public SkuMainData findById(Long id) {
        return skuMainDataPOTransfer.toDO(skuMainDataPORepository.findById(id).orElseThrow());
    }

    @Override
    public List<SkuMainData> findAllByIds(Collection<Long> skuMainDataIds) {
        return skuMainDataPOTransfer.toDOS(skuMainDataPORepository.findAllById(skuMainDataIds));
    }
}
