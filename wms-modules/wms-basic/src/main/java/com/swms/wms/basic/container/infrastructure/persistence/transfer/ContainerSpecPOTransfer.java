package com.swms.wms.basic.container.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.basic.container.domain.entity.Container;
import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerPO;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSlotPO;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSlotSpecPO;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSpecPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContainerSpecPOTransfer {
    ContainerSpec toContainerSpec(ContainerSpecPO containerSpecPO, List<ContainerSlotSpecPO> containerSlotSpecs);
}
