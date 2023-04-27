package com.swms.wms.basic.work_station.infrastructure.persistence.mapper;

import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallSlotPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PutWallSlotPORepository extends JpaRepository<PutWallSlotPO, Long> {

    List<PutWallSlotPO> findByStationCode(String stationCode);

    List<PutWallSlotPO> findByPutWallSlotCodeIn(List<String> putWallSlotCodes);

    PutWallSlotPO findByPutWallSlotCode(String putWallSlotCode);
}
