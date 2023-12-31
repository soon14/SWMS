package com.swms.mdm.api.main.data;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Collection;
import java.util.List;

public interface ISkuMainDataApi {

    void create(@Valid SkuMainDataDTO skuMainDataDTO);

    void update(@Valid SkuMainDataDTO skuMainDataDTO);

    SkuMainDataDTO getSkuMainData(@NotEmpty String skuCode, @NotEmpty String ownerCode);

    List<SkuMainDataDTO> getSkuMainData(@NotEmpty Collection<String> skuCodes);

    List<SkuMainDataDTO> getByIds(@NotEmpty Collection<Long> skuMainDataIds);
}
