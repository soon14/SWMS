package com.swms.wms.basic.work_station.infrastructure.persistence.mapper;

import com.swms.wms.basic.work_station.infrastructure.persistence.po.WorkStationPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkStationPORepository extends JpaRepository<WorkStationPO, Long> {
    WorkStationPO findByStationCode(String stationCode);
}
