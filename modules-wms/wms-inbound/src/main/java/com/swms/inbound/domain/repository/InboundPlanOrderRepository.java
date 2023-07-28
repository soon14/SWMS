package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.InboundPlanOrder;

public interface InboundPlanOrderRepository {

    void save(InboundPlanOrder inboundPlanOrder);

    InboundPlanOrder findById(Long inboundPlanOrderId);
}
