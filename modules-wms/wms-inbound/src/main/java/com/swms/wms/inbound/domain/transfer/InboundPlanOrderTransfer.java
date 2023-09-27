package com.swms.wms.inbound.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InboundPlanOrderTransfer {

    InboundPlanOrder toInboundPlanOrder(InboundPlanOrderDTO inboundPlanOrderDTO);

}
