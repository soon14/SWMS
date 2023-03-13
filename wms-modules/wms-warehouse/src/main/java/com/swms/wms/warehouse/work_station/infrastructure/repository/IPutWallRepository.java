package com.swms.wms.warehouse.work_station.infrastructure.repository;

import com.swms.wms.api.warehouse.dto.AssignOrdersDTO;
import com.swms.wms.warehouse.work_station.domain.entity.PutWall;
import com.swms.wms.warehouse.work_station.domain.entity.PutWallSlot;

import java.util.List;

public interface IPutWallRepository {
    void save(PutWall putWall);

    void update(PutWall putWall);

    List<PutWall> findByStationCode(String stationCode);

    PutWallSlot findByStationCodeAndSlotCode(String stationCode, String putWallSlotCode);

    void assignOrders(List<AssignOrdersDTO> assignOrdersDTOS);
}
