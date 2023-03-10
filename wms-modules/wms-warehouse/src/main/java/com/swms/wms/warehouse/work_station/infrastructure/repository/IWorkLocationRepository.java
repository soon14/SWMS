package com.swms.wms.warehouse.work_station.infrastructure.repository;

import com.swms.wms.warehouse.work_station.domain.entity.WorkLocation;

import java.util.List;

public interface IWorkLocationRepository {
    void save(WorkLocation workLocation);

    void update(WorkLocation workLocation);

    List<WorkLocation> findByStationCode(String stationCode);

    void deleteById(Long id);
}
