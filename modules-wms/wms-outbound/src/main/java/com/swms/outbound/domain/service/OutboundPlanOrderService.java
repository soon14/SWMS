package com.swms.outbound.domain.service;

import com.swms.outbound.domain.aggregate.OutboundWaveAggregate;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.entity.OutboundWave;

import java.util.List;

public interface OutboundPlanOrderService {
    void validateOutboundPlanOrder(OutboundPlanOrder outboundPlanOrder);

    List<OutboundWaveAggregate> wavePicking();
}
