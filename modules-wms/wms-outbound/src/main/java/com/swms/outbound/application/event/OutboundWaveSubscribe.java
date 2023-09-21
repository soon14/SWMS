package com.swms.outbound.application.event;

import static com.swms.common.utils.constants.RedisConstants.OUTBOUND_PLAN_ORDER_ASSIGNED_IDS;

import com.google.common.eventbus.Subscribe;
import com.swms.common.utils.utils.RedisUtils;
import com.swms.wms.api.outbound.event.OutboundPlanOrderAssignedEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OutboundWaveSubscribe {

    @Autowired
    private RedisUtils redisUtils;

    @Subscribe
    public void onEvent(@Valid OutboundPlanOrderAssignedEvent event) {
        String redisKey = OUTBOUND_PLAN_ORDER_ASSIGNED_IDS + event.getWarehouseCode();
        redisUtils.push(redisKey, event.getOutboundPlanOrderId());
    }
}
