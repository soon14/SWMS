package com.swms.station.domain.service;

import com.swms.station.domain.persistence.entity.WorkStation;

public interface WorkStationService {

    WorkStation initWorkStation(Long workStationId);

    WorkStation getWorkStation(Long workStationId);

    WorkStation getOrThrow(Long workStationId);

    void save(WorkStation workStation);

    void delete(WorkStation workStation);
}
