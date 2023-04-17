package com.swms.wms.basic.warehouse.domain.repository;

import com.swms.wms.basic.warehouse.domain.entity.Warehouse;

public interface WarehouseRepository {
    Warehouse findByWarehouseCode(String warehouseCode);
}
