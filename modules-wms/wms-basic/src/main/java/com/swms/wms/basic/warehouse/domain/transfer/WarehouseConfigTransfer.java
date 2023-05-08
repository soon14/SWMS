package com.swms.wms.basic.warehouse.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.basic.dto.WarehouseConfigDTO;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WarehouseConfigTransfer {
    WarehouseConfig toWarehouseConfig(WarehouseConfigDTO warehouseConfigDTO);

    WarehouseConfigDTO toWarehouseConfigDTO(WarehouseConfig warehouseConfig);
}
