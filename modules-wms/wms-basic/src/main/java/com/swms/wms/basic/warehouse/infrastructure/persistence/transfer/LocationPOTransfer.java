package com.swms.wms.basic.warehouse.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.basic.warehouse.domain.entity.Aisle;
import com.swms.wms.basic.warehouse.domain.entity.Location;
import com.swms.wms.basic.warehouse.infrastructure.persistence.po.AislePO;
import com.swms.wms.basic.warehouse.infrastructure.persistence.po.LocationPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationPOTransfer {

    List<AislePO> toAislePOS(List<Aisle> aisles);

    List<LocationPO> toLocationPOS(List<Location> locations);

    List<Location> toLocationDOS(List<LocationPO> locationPOS);

    Location toLocationDO(LocationPO orElseThrow);
}
