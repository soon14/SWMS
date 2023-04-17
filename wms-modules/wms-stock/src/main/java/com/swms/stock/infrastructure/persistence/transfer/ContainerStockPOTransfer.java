package com.swms.stock.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.stock.infrastructure.persistence.po.ContainerStockPO;
import com.swms.stock.domain.entity.ContainerStock;
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
    ContainerStockPO toContainerStockPO(ContainerStock containerStock);

    ContainerStock toContainerStock(ContainerStockPO containerStockPO);

    List<ContainerStock> toContainerStocks(List<ContainerStockPO> containerStockPOS);
}
