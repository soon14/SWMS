package com.swms.wms.stock.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.stock.dto.StockTransferDTO;
import com.swms.wms.stock.domain.entity.ContainerStock;
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
public interface ContainerStockTransfer {

    List<ContainerStock> fromCreateDTOsToDOs(List<StockCreateDTO> stockCreateDTOS);

    @Mapping(source = "transferQty", target = "totalQty")
    @Mapping(source = "transferQty", target = "availableQty")
    @Mapping(source = "targetContainerCode", target = "containerCode")
    @Mapping(source = "targetContainerSlotCode", target = "containerSlotCode")
    ContainerStock fromCreateDTOtoDO(StockCreateDTO stockCreateDTO);

    List<ContainerStock> toDOs(List<StockTransferDTO> stockTransferDTOS);

    @Mapping(source = "transferQty", target = "totalQty")
    @Mapping(source = "transferQty", target = "availableQty")
    @Mapping(source = "targetContainerCode", target = "containerCode")
    @Mapping(source = "targetContainerSlotCode", target = "containerSlotCode")
    ContainerStock toDO(StockTransferDTO stockTransferDTO);

    List<ContainerStockDTO> toDTOs(List<ContainerStock> containerStocks);
}
