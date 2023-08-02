package com.swms.station.business.model;

import com.google.common.collect.Maps;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.station.remote.WorkStationService;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class WorkStationManagement {

    private static final Map<Long, WorkStation> workStationMap = Maps.newConcurrentMap();

    @Autowired
    private WorkStationService workStationService;

    public synchronized WorkStation initWorkStation(Long workStationId) {

        WorkStationDTO workStationObj = workStationService.queryWorkStation(workStationId);
        WorkStation workStation = JsonUtils.string2Object(JsonUtils.obj2String(workStationObj), WorkStation.class);
        workStationMap.put(workStationId, workStation);

        return workStation;
    }

    public WorkStation getWorkStation(Long workStationId) {
        return workStationMap.get(workStationId);
    }

    public void removeWorkStation(Long workStationId) {
        workStationMap.remove(workStationId);
    }

    public Set<Long> getOperatingWorkStations() {
        return workStationMap.keySet();
    }
}
