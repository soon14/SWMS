package com.swms.wms.basic.work_station.infrastructure.persistence.mapper;

import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PutWallPORepository extends JpaRepository<PutWallPO, Long> {
    PutWallPO findByStationCode(String stationCode);
}
