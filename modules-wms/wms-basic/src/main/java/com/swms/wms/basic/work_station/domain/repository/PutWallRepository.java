package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWall;

import java.util.List;

public interface PutWallRepository {

    void save(PutWall putWall);

    PutWall findByStationCode(String stationCode);

    List<PutWallDTO.PutWallSlot> getPutWallSlotsByStationCode(String stationCode);

    PutWall findByPutWallCode(String putWallCode);

    List<PutWallDTO.PutWallSlot> findByPutWallSlotCodeIn(List<String> putWallSlotCodes);

    void saveAll(List<PutWallDTO.PutWallSlot> putWallSlots);

    PutWallDTO.PutWallSlot findByPutWallSlotCode(String putWallSlotCode);

    void save(PutWallDTO.PutWallSlot putWallSlot);
}
