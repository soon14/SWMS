package com.swms.wms.stock.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.stock.infrastructure.persistence.po.SkuBatchStockPO;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
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
    SkuBatchStockPO toPO(SkuBatchStock skuBatchStock);

    List<SkuBatchStockPO> toPOs(List<SkuBatchStock> skuBatchStocks);

    SkuBatchStock toDO(SkuBatchStockPO skuBatchStockPO);

    List<SkuBatchStock> toDOS(List<SkuBatchStockPO> skuBatchStockPOS);
}
