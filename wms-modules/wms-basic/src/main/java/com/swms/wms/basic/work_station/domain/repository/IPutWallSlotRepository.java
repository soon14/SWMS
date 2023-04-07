package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
import com.swms.wms.api.basic.dto.PutWallSlotDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWallSlot;

import java.util.List;

public interface IPutWallSlotRepository {
    void saveAll(List<PutWallSlot> putWallSlots);

    void updateAll(List<PutWallSlot> putWallSlots);

    List<PutWallSlotDTO> findAllPutWallSlotsByStationCode(String stationCode);

    PutWallSlot findByStationCodeAndSlotCode(String stationCode, String putWallSlotCode);

    void updateTransferContainerAndStatus(BindContainerDTO bindContainerDTO, PutWallSlotStatusEnum bound);
}
