package com.swms.station.remote;

import com.swms.common.utils.constants.RedisConstants;
import com.swms.mq.redis.RedisListener;
import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.websocket.utils.StationWebSocketUtils;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.basic.dto.WorkStationConfigDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WorkStationMqConsumer {

    @Autowired
    private WorkStationService workStationService;

    @RedisListener(topic = RedisConstants.STATION_LISTEN_WORK_STATION_CONFIG_UPDATE, type = WorkStationConfigDTO.class)
    public void listenWorkStationConfigUpdated(String topic, WorkStationConfigDTO workStationConfigDTO) {
        if (workStationConfigDTO == null) {
            return;
        }

        WorkStation workStation = workStationService.getWorkStation(workStationConfigDTO.getId());
        if (workStation == null) {
            return;
        }

        workStation.updateWorkStationConfig(workStationConfigDTO);

        workStationService.save(workStation);
    }

    @RedisListener(topic = RedisConstants.STATION_LISTEN_ORDER_ASSIGNED, type = List.class)
    public void listenOrderAssigned(String topic, List<PutWallDTO.PutWallSlot> putWallSlotDTOS) {

        if (CollectionUtils.isEmpty(putWallSlotDTOS)) {
            return;
        }

        Map<Long, List<PutWallDTO.PutWallSlot>> stationCodeMap = putWallSlotDTOS.stream().collect(Collectors.groupingBy(PutWallDTO.PutWallSlot::getWorkStationId));

        stationCodeMap.forEach((workStationId, values) -> {
            WorkStation workStation = workStationService.getWorkStation(workStationId);
            if (workStation == null) {
                return;
            }

            values.forEach(putWallSlotDTO -> workStation.getPutWalls()
                .forEach(putWall -> putWall.getPutWallSlots()
                    .forEach(cachePutWallSlot -> {
                        if (StringUtils.equals(putWallSlotDTO.getPutWallSlotCode(), cachePutWallSlot.getPutWallSlotCode())) {
                            cachePutWallSlot.setOrderIds(putWallSlotDTO.getOrderIds());
                            cachePutWallSlot.setPutWallSlotStatus(putWallSlotDTO.getPutWallSlotStatus());
                        }
                    })));

            workStationService.save(workStation);

            StationWebSocketUtils.noticeWebStationStatusChanged(workStationId);
        });
    }
}
