package com.swms.stock.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.stock.infrastructure.persistence.po.SkuBatchStockPO;
import com.swms.stock.domain.entity.SkuBatchStock;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkuBatchStockPOTransfer {
    SkuBatchStockPO toSkuBatchStockPO(SkuBatchStock skuBatchStock);

    SkuBatchStock toSkuBatchStock(SkuBatchStockPO skuBatchStockPO);

    List<SkuBatchStock> toSkuBatchStocks(List<SkuBatchStockPO> skuBatchStockPOS);
}
