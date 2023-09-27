package com.swms.wms.outbound.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.wms.api.outbound.event.NewOperationTaskEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OperationTaskSubscribe {

    @Subscribe
    public void onCreateEvent(@Valid NewOperationTaskEvent event) {
        //TODO create container transport tasks in automated warehouse or generate path in manual warehouse
    }
}
