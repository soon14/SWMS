package com.swms.wms.basic.container.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.basic.dto.ContainerLayoutDTO;
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
    ContainerLayoutDTO toContainerLayoutDTO(ContainerSpec containerSpec);

    List<ContainerLayoutDTO.ContainerSlotLayoutDTO> toContainerSlotLayoutDTOS(List<ContainerSpec.ContainerSlotSpec> containerSlotSpecs);
}
