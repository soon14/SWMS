package com.swms.mdm.main.data.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.main.data.domain.entity.OwnerMainData;
import com.swms.mdm.main.data.infrastructure.persistence.po.OwnerMainDataPO;
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
public interface OwnerMainDataPOTransfer {

    @Mapping(target = ".", source = "address")
    @Mapping(target = ".", source = "contactor")
    OwnerMainDataPO toPO(OwnerMainData ownerMainData);

    @InheritInverseConfiguration
    OwnerMainData toDO(OwnerMainDataPO ownerMainDataPO);

    List<OwnerMainData> toDOS(List<OwnerMainDataPO> ownerMainDataPOS);
}
