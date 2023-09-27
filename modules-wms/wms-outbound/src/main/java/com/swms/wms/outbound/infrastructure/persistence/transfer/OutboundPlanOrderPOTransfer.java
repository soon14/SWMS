package com.swms.wms.outbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrderDetail;
import com.swms.wms.outbound.infrastructure.persistence.po.OutboundPlanOrderDetailPO;
import com.swms.wms.outbound.infrastructure.persistence.po.OutboundPlanOrderPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OutboundPlanOrderPOTransfer {
    OutboundPlanOrderPO toPO(OutboundPlanOrder outboundPlanOrder);

    List<OutboundPlanOrderDetailPO> toDetailPOS(List<OutboundPlanOrderDetail> details);

    List<OutboundPlanOrderPO> toPOS(List<OutboundPlanOrder> outboundPlanOrders);

    OutboundPlanOrder toDO(OutboundPlanOrderPO outboundPlanOrderPO, List<OutboundPlanOrderDetailPO> details);

    List<OutboundPlanOrder> toDOs(List<OutboundPlanOrderPO> outboundPlanOrderPOS);
}
