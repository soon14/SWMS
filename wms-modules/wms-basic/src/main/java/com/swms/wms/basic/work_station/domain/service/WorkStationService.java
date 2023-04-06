package com.swms.wms.basic.work_station.domain.service;

import com.swms.wms.basic.work_station.domain.entity.WorkStation;
import com.swms.wms.basic.work_station.domain.repository.IWorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkStationService {

    @Autowired
    private IWorkStationRepository iWorkStationRepository;

    public void addWorkStation(WorkStation workStation) {
        iWorkStationRepository.addWorkStation(workStation);
    }

    public void updateWorkStation(WorkStation workStation) {
        iWorkStationRepository.updateWorkStation(workStation);
    }

    public WorkStation getStation(String stationCode) {
        return iWorkStationRepository.findByStationCode(stationCode);
    }
}
