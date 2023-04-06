package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.basic.work_station.domain.entity.WorkStation;

public interface IWorkStationRepository {
    void addWorkStation(WorkStation workStation);

    void updateWorkStation(WorkStation workStation);

    WorkStation findByStationCode(String stationCode);
}
