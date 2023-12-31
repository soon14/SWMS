package com.swms.mdm.main.data.application;

import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.main.data.domain.entity.SkuMainData;
import com.swms.mdm.main.data.domain.repository.SkuMainDataRepository;
import com.swms.mdm.main.data.domain.transfer.SkuMainDataTransfer;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

@Validated
@Service
@DubboService
public class SkuMainDataApiImpl implements ISkuMainDataApi {

    @Autowired
    private SkuMainDataRepository skuMainDataRepository;

    @Autowired
    private SkuMainDataTransfer skuMainDataTransfer;

    @Override
    public void create(SkuMainDataDTO skuMainDataDTO) {
        skuMainDataRepository.save(skuMainDataTransfer.toSkuMainData(skuMainDataDTO));
    }

    @Override
    public void update(SkuMainDataDTO skuMainDataDTO) {
        skuMainDataRepository.save(skuMainDataTransfer.toSkuMainData(skuMainDataDTO));
    }

    @Override
    public SkuMainDataDTO getSkuMainData(String skuCode, String ownerCode) {
        SkuMainData skuMainData = skuMainDataRepository.getSkuMainData(skuCode, ownerCode);
        return skuMainDataTransfer.toSkuMainDataDTO(skuMainData);
    }

    @Override
    public List<SkuMainDataDTO> getSkuMainData(Collection<String> skuCodes) {
        List<SkuMainData> skuMainDataList = skuMainDataRepository.getSkuMainData(skuCodes);
        return skuMainDataTransfer.toSkuMainDataDTOS(skuMainDataList);
    }

    @Override
    public List<SkuMainDataDTO> getByIds(Collection<Long> skuMainDataIds) {
        return skuMainDataTransfer.toSkuMainDataDTOS(skuMainDataRepository.findAllByIds(skuMainDataIds));
    }
}
