package com.swms.wms.inbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.inbound.domain.entity.InboundPlanOrderDetail;
import com.swms.wms.inbound.infrastructure.persistence.po.InboundPlanOrderDetailPO;
import com.swms.wms.inbound.infrastructure.persistence.po.InboundPlanOrderPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InboundPlanOrderPOTransfer {

    InboundPlanOrderPO toOrderPO(InboundPlanOrder inboundPlanOrder);

    List<InboundPlanOrderDetailPO> toDetailPOs(List<InboundPlanOrderDetail> inboundPlanOrderDetails);

    @Mapping(target = "details", source = "inboundPlanOrderDetails")
    InboundPlanOrder toDO(InboundPlanOrderPO inboundPlanOrderPO, List<InboundPlanOrderDetailPO> inboundPlanOrderDetails);

    List<InboundPlanOrder> toDOs(Collection<InboundPlanOrderPO> inboundPlanOrderPOS);
}
