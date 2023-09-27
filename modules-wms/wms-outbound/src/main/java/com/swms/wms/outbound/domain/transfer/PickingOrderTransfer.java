package com.swms.wms.outbound.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.outbound.domain.entity.PickingOrder;
import com.swms.wms.api.outbound.dto.PickingOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PickingOrderTransfer {
    List<PickingOrder> toDOs(List<PickingOrderDTO> pickingOrderDTOS);

    List<PickingOrderDTO> toDTOs(List<PickingOrder> pickingOrders);
}
