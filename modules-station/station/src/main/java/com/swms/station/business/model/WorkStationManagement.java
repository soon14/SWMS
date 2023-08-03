package com.swms.station.business.model;

import com.google.common.collect.Maps;
import com.swms.station.remote.WorkStationService;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorkStationManagement {

    private static final Map<Long, WorkStation> workStationMap = Maps.newConcurrentMap();

    @Autowired
    private WorkStationService workStationService;

    public synchronized WorkStation initWorkStation(Long workStationId) {

        WorkStationDTO workStationDTO = workStationService.queryWorkStation(workStationId);
        WorkStation workStation = new WorkStation();
        BeanUtils.copyProperties(workStationDTO, workStation);
        workStationMap.put(workStationId, workStation);

        return workStation;
    }

    public WorkStation getWorkStation(Long workStationId) {
        return workStationMap.get(workStationId);
    }

    public void removeWorkStation(Long workStationId) {
        workStationMap.remove(workStationId);
    }

}
