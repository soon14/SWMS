package com.swms.wms.basic.warehouse.domain.repository;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseAreaGroup;

public interface WarehouseAreaGroupRepository {

    void save(WarehouseAreaGroup warehouseAreaGroup);

    WarehouseAreaGroup getById(Long id);

    void delete(WarehouseAreaGroup warehouseAreaGroup);
}
