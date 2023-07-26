package com.swms.wms.basic.warehouse.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.basic.dto.AisleDTO;
import com.swms.wms.api.basic.dto.LocationDTO;
import com.swms.wms.basic.warehouse.domain.entity.Aisle;
import com.swms.wms.basic.warehouse.domain.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationTransfer {

    List<Location> toLocations(List<LocationDTO> locationDTOS);

    List<Aisle> toAisles(List<AisleDTO> aisleDTOS);

    List<LocationDTO> toLocationDTOS(List<Location> locations);

}
