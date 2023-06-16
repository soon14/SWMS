package com.swms.mdm.main.data.infrastructure.persistence.transfer;


import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.main.data.domain.entity.WarehouseMainData;
import com.swms.mdm.main.data.infrastructure.persistence.po.WarehouseMainDataPO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WarehouseMainDataPOTransfer {

    @Mapping(target = ".", source = "addressDTO")
    @Mapping(target = ".", source = "contactorDTO")
    WarehouseMainDataPO toPO(WarehouseMainData warehouse);

    @InheritInverseConfiguration
    WarehouseMainData toDO(WarehouseMainDataPO warehouseMainDataPO);
}
