package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.basic.work_station.domain.entity.WorkStation;

import java.util.List;

public interface WorkStationRepository {

    void save(WorkStation workStation);

    WorkStation findById(Long id);

    List<WorkStation> findByWarehouseCode(String warehouseCode);
}
