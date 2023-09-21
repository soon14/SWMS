package com.swms.outbound.application.scheduler;

import static com.swms.common.utils.constants.RedisConstants.OUTBOUND_PLAN_ORDER_ASSIGNED_IDS;

import com.alibaba.ttl.TtlRunnable;
import com.swms.common.utils.utils.RedisUtils;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.outbound.domain.aggregate.OutboundWaveAggregate;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.outbound.domain.service.OutboundWaveService;
import com.swms.wms.api.outbound.constants.OutboundPlanOrderStatusEnum;
import com.swms.wms.api.outbound.event.NewOutboundWaveEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class OutboundWaveScheduler {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private OutboundWaveService outboundWaveService;

    @Autowired
    private OutboundWaveAggregate outboundWaveAggregate;

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;

    @Autowired
    private DomainEventPublisher publisher;

    @Autowired
    @Qualifier("wavePickingExecutor")
    private Executor wavePickingExecutor;

    @Scheduled(cron = "*/30 * * * * *")
    public void wavePicking() {

        List<String> keys = redisUtils.keys(OUTBOUND_PLAN_ORDER_ASSIGNED_IDS);
        keys.forEach(key -> {
            List<Long> orderIds = redisUtils.getList(key);
            if (CollectionUtils.isEmpty(orderIds)) {
                return;
            }

            CompletableFuture.runAsync(Objects.requireNonNull(TtlRunnable.get(() ->
                this.wavePickingSingleWarehouse(orderIds, key))), wavePickingExecutor);
        });
    }

    public void wavePickingSingleWarehouse(List<Long> orderIds, String key) {

        List<OutboundPlanOrder> outboundPlanOrders = outboundPlanOrderRepository.findAllByIds(orderIds);

        List<OutboundPlanOrder> newOrders = outboundPlanOrders.stream()
            .filter(v -> v.getOutboundPlanOrderStatus() == OutboundPlanOrderStatusEnum.ASSIGNED).toList();
        if (CollectionUtils.isEmpty(newOrders)) {
            log.error("lists can't be empty, there maybe something error. remove it from redis.");
            redisUtils.removeList(key, orderIds);
            return;
        }

        List<List<OutboundPlanOrder>> lists = outboundWaveService.wavePickings(newOrders);
        if (CollectionUtils.isEmpty(lists)) {
            return;
        }

        lists.forEach(list -> {
            String waveNo = outboundWaveAggregate.waveOrders(list);
            publisher.sendAsyncEvent(new NewOutboundWaveEvent().setWaveNo(waveNo));
            redisUtils.removeList(key, list.stream().map(OutboundPlanOrder::getId).toList());
        });
    }
}
