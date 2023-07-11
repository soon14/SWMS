package com.swms.wms.basic.warehouse.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseLogic;
import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseLogicPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WarehouseLogicPOTransfer {

    WarehouseLogicPO toPO(WarehouseLogic warehouseLogic);

    WarehouseLogic toDO(WarehouseLogicPO warehouseLogicPO);
}
