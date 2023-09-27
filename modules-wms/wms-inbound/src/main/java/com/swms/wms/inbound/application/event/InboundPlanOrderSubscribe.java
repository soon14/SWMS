package com.swms.wms.inbound.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.domain.event.annotation.DomainTransaction;
import com.swms.wms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.wms.api.inbound.event.AcceptEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InboundPlanOrderSubscribe {

    @Autowired
    private InboundPlanOrderRepository inboundPlanOrderRepository;

    @Subscribe
    @DomainTransaction
    public void onEvent(@Valid AcceptEvent event) {

        log.info("inbound module receive event: " + event.toString());

        InboundPlanOrder inboundPlanOrder = inboundPlanOrderRepository.findById(event.getInboundPlanOrderId());
        inboundPlanOrder.accept(event);

        inboundPlanOrderRepository.saveOrderAndDetail(inboundPlanOrder);
    }
}
