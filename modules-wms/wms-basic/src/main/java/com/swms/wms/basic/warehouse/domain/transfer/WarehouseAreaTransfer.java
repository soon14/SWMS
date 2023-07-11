package com.swms.wms.basic.warehouse.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.basic.dto.WarehouseAreaDTO;
import com.swms.wms.api.basic.dto.WarehouseConfigDTO;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseConfig;
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
public interface WarehouseAreaTransfer {

    WarehouseArea toDO(WarehouseAreaDTO warehouseAreaDTODTO);

    WarehouseAreaDTO toDTO(WarehouseArea warehouseArea);
}
