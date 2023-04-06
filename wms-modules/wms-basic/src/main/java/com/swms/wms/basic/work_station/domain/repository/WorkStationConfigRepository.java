package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.basic.work_station.domain.entity.WorkStationConfig;

public interface WorkStationConfigRepository {
    void save(WorkStationConfig workStationConfig);

    WorkStationConfig findByStationCode(String stationCode);

    void update(WorkStationConfig workStationConfig);
}
