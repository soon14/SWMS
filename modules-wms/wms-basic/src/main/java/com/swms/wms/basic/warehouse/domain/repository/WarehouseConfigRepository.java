package com.swms.wms.basic.warehouse.domain.repository;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseConfig;

public interface WarehouseConfigRepository {
    void save(WarehouseConfig toWarehouseConfig);

    WarehouseConfig findByWarehouseCode(String warehouseCode);
}
