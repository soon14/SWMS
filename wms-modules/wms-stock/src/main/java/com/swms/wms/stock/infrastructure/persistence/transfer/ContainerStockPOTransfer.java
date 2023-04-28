package com.swms.wms.stock.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.stock.domain.entity.ContainerStock;
import com.swms.wms.stock.infrastructure.persistence.po.ContainerStockPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContainerStockPOTransfer {
    ContainerStockPO toPO(ContainerStock containerStock);

    ContainerStock toDO(ContainerStockPO containerStockPO);

    List<ContainerStock> toDOs(List<ContainerStockPO> containerStockPOs);

    List<ContainerStockPO> toPOs(List<ContainerStock> containerStocks);
}
