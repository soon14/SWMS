package com.swms.wms.warehouse.work_station.infrastructure.repository;

import com.swms.wms.warehouse.work_station.domain.entity.WorkStationConfig;

public interface WorkStationConfigRepository {
    void save(WorkStationConfig workStationConfig);

    WorkStationConfig findByStationCode(String stationCode);

    void update(WorkStationConfig workStationConfig);
}
