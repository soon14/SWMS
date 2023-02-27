package com.swms.station.business.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorkStationManagement {

    private static Map<String, WorkStation> workStationMap = Maps.newConcurrentMap();

    public synchronized WorkStation initWorkStation(String stationCode) {
//        workStationMap.get(stationCode,null);
        return null;
    }

    public WorkStation getWorkStation(String stationCode) {
        return workStationMap.get(stationCode);
    }

    public void updateWorkStation(WorkStation workStation) {
        Preconditions.checkNotNull(workStation);
        Preconditions.checkState(StringUtils.isNotEmpty(workStation.getStationCode()));
        workStationMap.put(workStation.getStationCode(), workStation);
    }
}
