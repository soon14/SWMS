package com.swms.wms.basic.warehouse.domain.repository;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;

public interface WarehouseAreaRepository {
    void save(WarehouseArea warehouseArea);

    WarehouseArea getById(Long warehouseAreaId);
}
