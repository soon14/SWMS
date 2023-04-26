package com.swms.wms.basic.container.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.basic.dto.ContainerDTO;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContainerSpecTransfer {

    List<ContainerDTO.ContainerSlot> toContainerSlots(List<ContainerSpecDTO.ContainerSlotSpec> containerSlotSpecs);

    ContainerSpec toDO(ContainerSpecDTO containerSpecDTO);

    ContainerSpecDTO toDTO(ContainerSpec containerSpec);
}
