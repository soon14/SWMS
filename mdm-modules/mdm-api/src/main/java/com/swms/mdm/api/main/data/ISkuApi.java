package com.swms.mdm.api.main.data;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

public interface ISkuApi {

    void create(@Valid SkuMainDataDTO skuMainDataDTO);

    void update(@Valid SkuMainDataDTO skuMainDataDTO);

    SkuMainDataDTO getSkuMainData(@NotEmpty String skuCode, @NotEmpty String ownerCode);

    List<SkuMainDataDTO> getSkuMainData(@NotEmpty Collection<String> skuCodes);
}
