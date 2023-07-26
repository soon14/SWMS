package com.swms.wms.basic.warehouse.domain.repository;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;

import java.util.List;

public interface WarehouseAreaRepository {

    void save(WarehouseArea warehouseArea);

    WarehouseArea getById(Long warehouseAreaId);

    List<WarehouseArea> getByWarehouseAreaGroup(String warehouseAreaGroupCode, String warehouseCode);
}
