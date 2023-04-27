package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.basic.work_station.domain.entity.WorkStation;

public interface WorkStationRepository {

    void save(WorkStation workStation);

    WorkStation findByStationCode(String stationCode);
}
