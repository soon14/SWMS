package com.swms.wms.warehouse.work_station.domain.service;

import com.swms.wms.warehouse.work_station.domain.entity.WorkStationConfig;
import com.swms.wms.warehouse.work_station.infrastructure.repository.WorkStationConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkStationConfigService {

    @Autowired
    private WorkStationConfigRepository workStationConfigRepository;

    public void addWorkStationConfig(WorkStationConfig workStationConfig) {
        workStationConfigRepository.save(workStationConfig);
    }

    public WorkStationConfig getWorkStationConfigByStationCode(String stationCode) {
        return workStationConfigRepository.findByStationCode(stationCode);
    }

    public void updateWorkStationConfig(WorkStationConfig workStationConfig) {
        workStationConfigRepository.update(workStationConfig);
    }
}
