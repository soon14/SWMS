package com.swms.wms.warehouse.work_station.infrastructure.repository;

import com.swms.wms.warehouse.work_station.domain.entity.PutWallSlot;

import java.util.List;

public interface IPutWallSlotRepository {
    void saveAll(List<PutWallSlot> putWallSlots);

    void updateAll(List<PutWallSlot> putWallSlots);
}
