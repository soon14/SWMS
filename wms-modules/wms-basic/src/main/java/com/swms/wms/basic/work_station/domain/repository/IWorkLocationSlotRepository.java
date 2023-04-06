package com.swms.wms.basic.work_station.domain.repository;

import com.swms.wms.basic.work_station.domain.entity.WorkLocationSlot;

import java.util.List;

public interface IWorkLocationSlotRepository {
    void saveAll(List<WorkLocationSlot> workLocationSlots);

    void updateAll(List<WorkLocationSlot> workLocationSlots);
}
