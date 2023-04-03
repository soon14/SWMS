package com.swms.stock.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.stock.domain.entity.SkuBatchStock;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkuBatchStockTransfer {

    @Mapping(source = "transferQty", target = "totalQty")
    @Mapping(source = "transferQty", target = "availableQty")
    List<SkuBatchStock> toSkuBatchStocks(List<StockTransferDTO> stockTransferDTOS);
}
