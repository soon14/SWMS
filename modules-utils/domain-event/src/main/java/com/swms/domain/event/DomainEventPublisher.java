package com.swms.domain.event;

import com.google.common.eventbus.EventBus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DomainEventPublisher {

    @Resource(name = "asyncEventBus")
    private EventBus asyncEventBus;

    @Resource(name = "syncEventBus")
    private EventBus syncEventBus;

    public void sendAsyncEvent(Object event) {
        log.debug("Sending event: " + event.toString());
        asyncEventBus.post(event);
    }

    public void sendSyncEvent(Object event) {
        syncEventBus.post(event);
    }
}
