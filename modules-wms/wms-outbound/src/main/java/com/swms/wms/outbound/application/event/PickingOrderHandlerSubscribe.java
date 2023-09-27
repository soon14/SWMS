package com.swms.wms.outbound.application.event;

import static com.swms.common.utils.constants.RedisConstants.NEW_PICKING_ORDER_IDS;

import com.google.common.eventbus.Subscribe;
import com.swms.common.utils.utils.RedisUtils;
import com.swms.wms.api.outbound.event.NewPickingOrdersEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PickingOrderHandlerSubscribe {

    @Autowired
    private RedisUtils redisUtils;

    @Subscribe
    public void onEvent(@Valid NewPickingOrdersEvent event) {
        String redisKey = NEW_PICKING_ORDER_IDS + "_" + event.getWarehouseCode();
        redisUtils.pushAll(redisKey, event.getPickingOrderNos());
    }
}
