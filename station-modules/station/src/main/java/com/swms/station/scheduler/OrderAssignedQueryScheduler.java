package com.swms.station.scheduler;

import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.websocket.utils.StationWebSocketUtils;
import com.swms.wms.api.warehouse.IWorkStationApi;
import com.swms.wms.api.warehouse.dto.PutWallSlotDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class OrderAssignedQueryScheduler {

    @Autowired
    private IWorkStationApi iWorkStationApi;

    @Autowired
    private WorkStationManagement workStationManagement;

    @Scheduled
    public void queryAssignedOrders() {
        Set<String> operatingWorkStations = workStationManagement.getOperatingWorkStations();
        if (CollectionUtils.isEmpty(operatingWorkStations)) {
            return;
        }

        operatingWorkStations.forEach(stationCode -> {
            List<PutWallSlotDTO> putWallSlots = iWorkStationApi.getPutWallSlots(stationCode);
            WorkStation workStation = workStationManagement.getWorkStation(stationCode);

            AtomicBoolean refresh = new AtomicBoolean(false);
            workStation.getPutWalls().forEach(putWall -> {
                // compare cache slots with remote slots, set orderIds and slot status
                putWall.getPutWallSlots().forEach(cachePutWallSlot -> {
                    if (compareAndSet(putWallSlots, cachePutWallSlot)) {
                        refresh.set(true);
                    }
                });

                if (refresh.get()) {
                    StationWebSocketUtils.noticeWebStationStatusChanged(stationCode);
                }
            });
        });
    }

    private boolean compareAndSet(List<PutWallSlotDTO> remoteOutWallSlots, PutWallSlotDTO cachePutWallSlot) {

        AtomicBoolean refresh = new AtomicBoolean(false);
        remoteOutWallSlots.stream().filter(remotePutWallSlot ->
                StringUtils.equals(remotePutWallSlot.getSlotCode(), cachePutWallSlot.getSlotCode()) &&
                    CollectionUtils.isNotEmpty(remotePutWallSlot.getOrderIds())
                    && CollectionUtils.isEmpty(cachePutWallSlot.getOrderIds()))
            .findFirst().ifPresent(remotePutWallSlot -> {
                cachePutWallSlot.setOrderIds(remotePutWallSlot.getOrderIds());
                cachePutWallSlot.setPutWallSlotStatus(remotePutWallSlot.getPutWallSlotStatus());

                refresh.set(true);
            });

        return refresh.get();
    }

}
