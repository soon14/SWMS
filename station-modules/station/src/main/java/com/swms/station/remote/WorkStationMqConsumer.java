package com.swms.station.remote;

import com.swms.utils.constants.RedisConstants;
import com.swms.utils.mq.redis.RedisListener;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.websocket.utils.StationWebSocketUtils;
import com.swms.wms.api.warehouse.dto.PutWallSlotDTO;
import com.swms.wms.api.warehouse.dto.WorkStationConfigDTO;
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
    private WorkStationManagement workStationManagement;

    @RedisListener(topic = RedisConstants.STATION_LISTEN_WORK_STATION_CONFIG_UPDATE, type = WorkStationConfigDTO.class)
    public void listenWorkStationConfigUpdated(String topic, WorkStationConfigDTO workStationConfigDTO) {
        if (workStationConfigDTO == null) {
            return;
        }

        WorkStation workStation = workStationManagement.getWorkStation(workStationConfigDTO.getStationCode());
        if (workStation == null) {
            return;
        }

        workStation.setWorkStationConfig(workStationConfigDTO);
    }

    @RedisListener(topic = RedisConstants.STATION_LISTEN_ORDER_ASSIGNED, type = List.class)
    public void listenOrderAssigned(String topic, List<PutWallSlotDTO> putWallSlotDTOS) {

        if (CollectionUtils.isEmpty(putWallSlotDTOS)) {
            return;
        }

        Map<String, List<PutWallSlotDTO>> stationCodeMap = putWallSlotDTOS.stream().collect(Collectors.groupingBy(PutWallSlotDTO::getStationCode));

        stationCodeMap.forEach((stationCode, values) -> {
            WorkStation workStation = workStationManagement.getWorkStation(stationCode);
            if (workStation == null) {
                return;
            }

            values.forEach(putWallSlotDTO -> workStation.getPutWalls().forEach(putWall -> {
                putWall.getPutWallSlots().forEach(cachePutWallSlot -> {
                    if (StringUtils.equals(putWallSlotDTO.getSlotCode(), cachePutWallSlot.getSlotCode())) {
                        cachePutWallSlot.setOrderIds(putWallSlotDTO.getOrderIds());
                        cachePutWallSlot.setPutWallSlotStatus(putWallSlotDTO.getPutWallSlotStatus());
                    }
                });
            }));
            StationWebSocketUtils.noticeWebStationStatusChanged(stationCode);
        });
    }
}
