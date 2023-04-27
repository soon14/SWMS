package com.swms.wms.basic.work_station.infrastructure.persistence.mapper;

import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PutWallPORepository extends JpaRepository<PutWallPO, Long> {
    PutWallPO findByStationCode(String stationCode);

    PutWallPO findByPutWallCode(String putWallCode);

}
