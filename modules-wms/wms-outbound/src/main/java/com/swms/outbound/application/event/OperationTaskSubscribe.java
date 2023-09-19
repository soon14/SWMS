package com.swms.outbound.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.wms.api.outbound.event.NewOperationTaskEvent;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OperationTaskSubscribe {

    @Subscribe
    public void onEvent(@Valid NewOperationTaskEvent event) {
        List<OperationTaskDTO> operationTasks = event.getOperationTasks();

        //TODO create container transport tasks in automated warehouse or generate path in manual warehouse
    }
}
