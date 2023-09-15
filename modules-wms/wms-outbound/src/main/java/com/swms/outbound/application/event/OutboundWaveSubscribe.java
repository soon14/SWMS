package com.swms.outbound.application.event;

import static com.swms.common.utils.constants.RedisConstants.OUTBOUND_PLAN_ORDER_ASSIGNED_IDS;

import com.google.common.eventbus.Subscribe;
import com.swms.common.utils.utils.RedisUtils;
import com.swms.outbound.domain.aggregate.OutboundWaveAggregate;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.outbound.domain.service.OutboundWaveService;
import com.swms.wms.api.outbound.event.OutboundPlanOrderAssignedEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OutboundWaveSubscribe {

    @Autowired
    private OutboundWaveService outboundWaveService;

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;

    @Autowired
    private OutboundWaveAggregate outboundWaveAggregate;

    @Autowired
    private RedisUtils redisUtils;

    @Subscribe
    public void onEvent(@Valid OutboundPlanOrderAssignedEvent event) {

        String redisKey = OUTBOUND_PLAN_ORDER_ASSIGNED_IDS + event.getWarehouseCode();

        redisUtils.push(redisKey, event.getOutboundPlanOrderId());
        List<Long> orderIds = redisUtils.getList(redisKey);

        if (CollectionUtils.isEmpty(orderIds)) {
            log.error("orderIds can't be empty, there maybe something error.");
            return;
        }

        List<OutboundPlanOrder> outboundPlanOrders = outboundPlanOrderRepository.findAllByIds(orderIds);

        List<OutboundPlanOrder> emptyWaveNoOrders = outboundPlanOrders.stream()
            .filter(v -> StringUtils.isEmpty(v.getWaveNo())).toList();
        if (CollectionUtils.isEmpty(emptyWaveNoOrders)) {
            log.error("lists can't be empty, there maybe something error. remove it from redis.");
            redisUtils.removeList(redisKey, outboundPlanOrders
                .stream().map(OutboundPlanOrder::getId).toList());
            return;
        }

        List<List<OutboundPlanOrder>> lists = outboundWaveService.wavePickings(emptyWaveNoOrders);
        if (CollectionUtils.isEmpty(lists)) {
            return;
        }

        lists.forEach(list -> {
            outboundWaveAggregate.waveOrders(list);
            redisUtils.removeList(redisKey, list.stream().map(OutboundPlanOrder::getId).toList());
        });

    }
}
