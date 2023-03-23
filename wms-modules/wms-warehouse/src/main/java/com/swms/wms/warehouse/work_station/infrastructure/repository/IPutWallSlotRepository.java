package com.swms.wms.warehouse.work_station.infrastructure.repository;

import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.warehouse.constants.PutWallSlotStatusEnum;
import com.swms.wms.api.warehouse.dto.PutWallSlotDTO;
import com.swms.wms.warehouse.work_station.domain.entity.PutWallSlot;

import java.util.List;

public interface IPutWallSlotRepository {
    void saveAll(List<PutWallSlot> putWallSlots);

    void updateAll(List<PutWallSlot> putWallSlots);

    List<PutWallSlotDTO> findAllPutWallSlotsByStationCode(String stationCode);

    PutWallSlot findByStationCodeAndSlotCode(String stationCode, String putWallSlotCode);

    void updateTranferContainerAndStatus(BindContainerDTO bindContainerDTO, PutWallSlotStatusEnum bound);
}
