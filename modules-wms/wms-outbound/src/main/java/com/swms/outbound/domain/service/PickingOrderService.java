package com.swms.outbound.domain.service;

import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.entity.PickingOrder;

import java.util.List;

public interface PickingOrderService {
    List<PickingOrder> spiltWave(OutboundWave outboundWave);
}
