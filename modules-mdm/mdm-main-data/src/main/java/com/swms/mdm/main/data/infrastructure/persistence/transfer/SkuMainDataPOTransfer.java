package com.swms.mdm.main.data.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.main.data.domain.entity.SkuMainData;
import com.swms.mdm.main.data.infrastructure.persistence.po.SkuMainDataPO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkuMainDataPOTransfer {

    @Mapping(target = ".", source = "weight")
    @Mapping(target = ".", source = "volume")
    @Mapping(target = ".", source = "skuAttribute")
    @Mapping(target = ".", source = "skuConfig")
    SkuMainDataPO toPO(SkuMainData skuMainData);

    @InheritInverseConfiguration
    SkuMainData toDO(SkuMainDataPO skuMainDataPO);

    List<SkuMainData> toDOS(List<SkuMainDataPO> skuMainDataPOS);
}
