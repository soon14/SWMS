package com.swms.outbound.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.domain.event.annotation.DomainTransaction;
import com.swms.wms.api.outbound.event.NewOutboundWaveEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PickingOrderSubscribe {

    @Subscribe
    @DomainTransaction
    public void onEvent(@Valid NewOutboundWaveEvent event) {

    }
}
