package com.swms.stock.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.stock.domain.entity.SkuBatchStockLock;
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
public interface SkuBatchStockLockTransfer {

    List<SkuBatchStockLockDTO> toSkuBatchStockLockDTOS(List<SkuBatchStockLock> skuBatchStockLocks);

    List<SkuBatchStockLock> toSkuBatchStockLocks(List<SkuBatchStockLockDTO> skuBatchStockLockDTOS);
}
