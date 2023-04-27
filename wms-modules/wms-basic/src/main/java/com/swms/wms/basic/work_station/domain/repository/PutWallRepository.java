package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWall;

import java.util.List;

public interface PutWallRepository {

    void save(PutWall putWall);

    PutWall findByStationCode(String stationCode);

    List<PutWallDTO.PutWallSlot> getPutWallSlotsByStationCode(String stationCode);

    PutWall findByPutWallCode(String putWallCode);
}
