package com.swms.mdm.main.data.controller.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.main.data.controller.parameter.SkuMainDataVO;
import com.swms.mdm.main.data.domain.entity.SkuMainData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkuMainDataDTOTransfer {

    @Mapping(target = ".", source = "weight")
    @Mapping(target = ".", source = "volumeDTO")
    @Mapping(target = ".", source = "skuAttribute")
    @Mapping(target = ".", source = "skuConfig")
    SkuMainDataVO toVO(SkuMainData skuMainData);
}
