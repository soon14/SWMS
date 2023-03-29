package com.swms.inventory.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.inventory.domain.entity.ContainerStockLock;
import com.swms.inventory.domain.entity.SkuBatchStockLock;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContainerStockLockTransfer {

    List<ContainerStockLockDTO> toContainerStockLockDTOS(List<ContainerStockLock> containerStockLocks);

    List<ContainerStockLock> toContainerStockLocks(List<ContainerStockLockDTO> containerStockLockDTOS);
}
