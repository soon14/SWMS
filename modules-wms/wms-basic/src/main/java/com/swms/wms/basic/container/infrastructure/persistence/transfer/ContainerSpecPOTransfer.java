package com.swms.wms.basic.container.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSpecPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContainerSpecPOTransfer {
    ContainerSpec toContainerSpec(ContainerSpecPO containerSpecPO);

    ContainerSpecPO toPO(ContainerSpec containerSpec);
}
