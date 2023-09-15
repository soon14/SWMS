package com.swms.outbound.domain.service.impl;

import com.google.common.collect.Lists;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.service.OutboundWaveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboundWaveServiceImpl implements OutboundWaveService {

    @Override
    public List<List<OutboundPlanOrder>> wavePickings(List<OutboundPlanOrder> outboundPlanOrders) {
        List<List<OutboundPlanOrder>> orders = Lists.newArrayList();
        orders.add(outboundPlanOrders);
        return orders;
    }
}
