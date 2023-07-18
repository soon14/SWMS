package com.swms.wms.basic.work_station.infrastructure.persistence.mapper;

import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallSlotPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PutWallSlotPORepository extends JpaRepository<PutWallSlotPO, Long> {

    List<PutWallSlotPO> findByWorkStationId(Long workStationId);

    List<PutWallSlotPO> findByPutWallSlotCodeInAndWorkStationId(List<String> putWallSlotCodes, Long workStationId);

    List<PutWallSlotPO> findByPutWallCodeAndWorkStationId(String putWallCode, Long workStationId);

    PutWallSlotPO findByPutWallSlotCodeAndWorkStationId(String putWallSlotCode, Long workStationId);
}
