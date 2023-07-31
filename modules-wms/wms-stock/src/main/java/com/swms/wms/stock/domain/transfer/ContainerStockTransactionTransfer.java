package com.swms.wms.stock.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStockTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContainerStockTransactionTransfer {

    ContainerStockTransaction toDO(StockTransferDTO stockTransferDTO);

    List<ContainerStockTransaction> toDOS(List<StockCreateDTO> stockCreateDTOS);
}
