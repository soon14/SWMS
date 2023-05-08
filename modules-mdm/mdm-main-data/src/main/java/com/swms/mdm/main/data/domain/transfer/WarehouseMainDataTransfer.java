package com.swms.mdm.main.data.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.mdm.main.data.domain.entity.WarehouseMainData;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WarehouseMainDataTransfer {

    WarehouseMainData toWarehouseMainData(WarehouseMainDataDTO warehouseDTO);

    WarehouseMainDataDTO toDTO(WarehouseMainData warehouseMainData);
}
