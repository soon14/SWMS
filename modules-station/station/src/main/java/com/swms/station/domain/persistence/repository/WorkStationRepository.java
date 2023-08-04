package com.swms.station.domain.persistence.repository;

import com.swms.station.domain.persistence.entity.WorkStation;
import org.springframework.data.repository.CrudRepository;

public interface WorkStationRepository extends CrudRepository<WorkStation, Long> {
}
