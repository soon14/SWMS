package com.swms.wms.warehouse.work_station.infrastructure.repository;

import com.swms.wms.warehouse.work_station.domain.entity.PutWall;

import java.util.List;

public interface IPutWallRepository {
    void save(PutWall putWall);

    void update(PutWall putWall);

    List<PutWall> findByStationCode(String stationCode);
}
