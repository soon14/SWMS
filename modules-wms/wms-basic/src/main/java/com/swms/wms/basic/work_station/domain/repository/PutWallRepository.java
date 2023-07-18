package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWall;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface PutWallRepository {

    void save(PutWall putWall);

    List<PutWallDTO.PutWallSlot> getPutWallSlotsByWorkStationId(Long workStationId);

    PutWall findById(Long id);

    List<PutWallDTO.PutWallSlot> findByPutWallSlotCodeIn(List<String> putWallSlotCodes, @NotNull Long workStationId);

    void saveAll(List<PutWallDTO.PutWallSlot> putWallSlots);

    PutWallDTO.PutWallSlot findByPutWallSlotCode(String putWallSlotCode, @NotNull Long workStationId);

    void save(PutWallDTO.PutWallSlot putWallSlot);

    void deletePutWallSlots(List<PutWallDTO.PutWallSlot> putWallSlots);
}
