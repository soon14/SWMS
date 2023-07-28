package com.swms.wms.basic.warehouse.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.basic.dto.WarehouseLogicDTO;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseLogic;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WarehouseLogicTransfer {

    WarehouseLogic toDO(WarehouseLogicDTO warehouseLogicDTO);

    WarehouseLogicDTO toDTO(WarehouseLogic warehouseLogic);
}
