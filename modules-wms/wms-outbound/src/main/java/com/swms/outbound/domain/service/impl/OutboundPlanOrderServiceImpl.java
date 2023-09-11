package com.swms.outbound.domain.service.impl;

import com.swms.outbound.domain.aggregate.OutboundWaveAggregate;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.service.OutboundPlanOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboundPlanOrderServiceImpl implements OutboundPlanOrderService {
    @Override
    public void validateOutboundPlanOrder(OutboundPlanOrder outboundPlanOrder) {

    }

    @Override
    public List<OutboundWaveAggregate> wavePicking() {
        return null;
    }
}
