package com.swms.inbound.domain.service;

import com.swms.inbound.domain.entity.InboundPlanOrder;

public interface InboundPlanOrderService {
    void validateInboundPlanOrder(InboundPlanOrder inboundPlanOrder);

    void validateRepeatCustomerOrderNo(InboundPlanOrder inboundPlanOrder);
}
