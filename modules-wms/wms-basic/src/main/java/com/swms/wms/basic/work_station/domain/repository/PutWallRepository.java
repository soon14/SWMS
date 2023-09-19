package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.basic.work_station.domain.entity.PutWall;

import java.util.List;

public interface PutWallRepository {

    void save(PutWall putWall);

    void saveAll(List<PutWall> putWalls);

    PutWall findById(Long id);

    List<PutWall> findAllByWorkStationIds(List<Long> workStationIds);

    PutWall findByCodeAndWorkStationId(String putWallCode, Long workStationId);
}
