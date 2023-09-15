package com.swms.outbound.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OutboundPlanOrderTransfer {

    OutboundPlanOrder toDO(OutboundPlanOrderDTO outboundPlanOrderDTO);

    OutboundPlanOrderDTO toDTO(OutboundPlanOrder outboundPlanOrder);

    List<OutboundPlanOrderDTO> toDTOs(List<OutboundPlanOrder> outboundPlanOrders);

    List<List<OutboundPlanOrder>> toDOs(List<List<OutboundPlanOrderDTO>> outboundPlanOrderDTOs);
}
