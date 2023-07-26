package com.swms.wms.basic.warehouse.domain.repository;

import com.swms.wms.basic.warehouse.domain.entity.Aisle;
import com.swms.wms.basic.warehouse.domain.entity.Location;

import java.util.List;

public interface LocationRepository {
    List<Location> getAllByIds(List<Long> locationIds);

    void createLocations(List<Aisle> toAisles, List<Location> toLocations);

    void saveAll(List<Location> locations);

    Location getById(Long id);

    List<Location> getByAisle(String aisleCode, Long warehouseAreaId);
}
