package com.swms.wms.basic.warehouse.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseAreaGroup;
import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseAreaGroupPO;
import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehouseAreaPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WarehouseAreaGroupPOTransfer {

    WarehouseAreaGroupPO toPO(WarehouseAreaGroup warehouseAreaGroup);

    WarehouseAreaGroup toDO(WarehouseAreaGroupPO warehouseAreaGroupPO);
}
