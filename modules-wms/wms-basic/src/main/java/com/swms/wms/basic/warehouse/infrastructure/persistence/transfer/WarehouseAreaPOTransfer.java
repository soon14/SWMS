package com.swms.wms.basic.warehouse.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;
import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseAreaPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WarehouseAreaPOTransfer {

    WarehouseAreaPO toPO(WarehouseArea warehouseArea);

    WarehouseArea toDO(WarehouseAreaPO warehouseAreaPO);

    List<WarehouseArea> toDOS(List<WarehouseAreaPO> warehouseAreaPOS);

    List<WarehouseAreaPO> toPOS(List<WarehouseArea> warehouseAreas);
}
