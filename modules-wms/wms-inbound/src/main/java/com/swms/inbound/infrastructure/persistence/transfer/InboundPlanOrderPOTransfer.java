package com.swms.inbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.infrastructure.persistence.po.InboundPlanOrderDetailPO;
import com.swms.inbound.infrastructure.persistence.po.InboundPlanOrderPO;
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
public interface InboundPlanOrderPOTransfer {

    InboundPlanOrderPO toOrderPO(InboundPlanOrder inboundPlanOrder);

    List<InboundPlanOrderDetailPO> toDetailPOs(List<InboundPlanOrderDetailDTO> inboundPlanOrderDetails);
}
