package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;

public interface InboundPlanOrderRepository {
    void save(InboundPlanOrder inboundPlanOrder);

    int updateStatusWithOriginalStatus(Long inboundPlanOrderId, InboundPlanOrderStatusEnum targetStatus,
                                       InboundPlanOrderStatusEnum originalStatus);
}
