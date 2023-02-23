package com.swms.station.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderAssignedQueryScheduler {

    @Scheduled
    public void queryOrderAssigned() {
        // TODO document why this method is empty
    }
}
