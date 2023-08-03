package com.swms.wms.stock.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.SkuBatchStock;
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

    List<SkuBatchStock> toDOs(List<StockTransferDTO> stockTransferDTOS);

    @Mapping(source = "stockTransferDTO.transferQty", target = "totalQty")
    @Mapping(source = "stockTransferDTO.transferQty", target = "availableQty")
    SkuBatchStock toDO(StockTransferDTO stockTransferDTO, Long skuBatchStockId);

    @Mapping(source = "transferQty", target = "totalQty")
    @Mapping(source = "transferQty", target = "availableQty")
    SkuBatchStock fromCreateDTOtoDO(StockCreateDTO stockCreateDTO);
}
