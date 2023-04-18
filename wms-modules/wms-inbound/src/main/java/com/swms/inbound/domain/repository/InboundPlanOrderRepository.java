package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;

public interface InboundPlanOrderRepository {

    void save(InboundPlanOrder inboundPlanOrder);

    InboundPlanOrder findById(Long inboundPlanOrderId);
}
