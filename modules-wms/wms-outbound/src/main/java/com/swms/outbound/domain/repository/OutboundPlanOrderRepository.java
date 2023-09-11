package com.swms.outbound.domain.repository;

import com.swms.outbound.domain.entity.OutboundPlanOrder;

import java.util.List;

public interface OutboundPlanOrderRepository {
    void saveOutboundPlanOrder(OutboundPlanOrder outboundPlanOrder);

    void saveAll(List<OutboundPlanOrder> outboundPlanOrders);
}
