package com.swms.wms.warehouse.work_station.domain.repository;

import com.swms.wms.warehouse.work_station.domain.entity.WorkLocationSlot;

import java.util.List;

public interface IWorkLocationSlotRepository {
    void saveAll(List<WorkLocationSlot> workLocationSlots);

    void updateAll(List<WorkLocationSlot> workLocationSlots);
}
