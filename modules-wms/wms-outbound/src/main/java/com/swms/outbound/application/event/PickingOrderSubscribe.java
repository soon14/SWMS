package com.swms.outbound.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.outbound.domain.aggregate.PickingOrderWaveAggregate;
import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.wms.api.outbound.constants.OutboundWaveStatusEnum;
import com.swms.wms.api.outbound.event.NewOutboundWaveEvent;
import com.swms.wms.api.outbound.event.NewPickingOrdersEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PickingOrderSubscribe {

    @Autowired
    private OutboundWaveRepository outboundWaveRepository;

    @Autowired
    private PickingOrderWaveAggregate pickingOrderWaveAggregate;

    @Autowired
    private DomainEventPublisher publisher;

    @Subscribe
    public void onEvent(@Valid NewOutboundWaveEvent event) {
        OutboundWave outboundWave = outboundWaveRepository.findByWaveNo(event.getWaveNo());
        if (outboundWave.getWaveStatus() != OutboundWaveStatusEnum.NEW) {
            return;
        }
        List<PickingOrder> pickingOrders = pickingOrderWaveAggregate.split(outboundWave);

        publisher.sendAsyncEvent(new NewPickingOrdersEvent()
            .setPickingOrderNos(pickingOrders.stream().map(PickingOrder::getPickingOrderNo).toList())
            .setWarehouseCode(outboundWave.getWarehouseCode()));
    }
}
