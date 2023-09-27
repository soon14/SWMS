package com.swms.wms.outbound.domain.service;

import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;

import java.util.List;

public interface OutboundWaveService {

    List<List<OutboundPlanOrder>> wavePickings(List<OutboundPlanOrder> outboundPlanOrders);
}
