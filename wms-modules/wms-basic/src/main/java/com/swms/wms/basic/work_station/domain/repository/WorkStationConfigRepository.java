package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.basic.work_station.domain.entity.WorkStation;

public interface WorkStationConfigRepository {
    void save(WorkStation.WorkStationConfig workStationConfig);

    WorkStation.WorkStationConfig findByStationCode(String stationCode);

    void update(WorkStation.WorkStationConfig workStationConfig);
}
