package com.swms.mdm.api.main.data;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;

import java.util.Collection;
import java.util.List;

public interface ISkuApi {

    void create(SkuMainDataDTO skuMainDataDTO);

    void update(SkuMainDataDTO skuMainDataDTO);

    SkuMainDataDTO getSkuMainData(String skuCode, String ownerCode);

    List<SkuMainDataDTO> getSkuMainData(Collection<String> skuCodes);
}
