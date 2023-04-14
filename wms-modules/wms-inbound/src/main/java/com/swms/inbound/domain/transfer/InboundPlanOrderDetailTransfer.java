package com.swms.inbound.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.entity.InboundPlanOrderDetail;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InboundPlanOrderDetailTransfer {

    List<InboundPlanOrderDetail> toInboundPlanOrderDetails(List<InboundPlanOrderDetailDTO> inboundPlanOrderDetailDTOS);
}
