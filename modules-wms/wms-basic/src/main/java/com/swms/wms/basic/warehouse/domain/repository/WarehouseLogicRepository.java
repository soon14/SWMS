package com.swms.wms.basic.warehouse.domain.repository;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseLogic;

import java.util.List;

public interface WarehouseLogicRepository {

    void save(WarehouseLogic warehouseLogic);

    WarehouseLogic getById(Long id);

    List<WarehouseLogic> getByWarehouseAreaIds(List<Long> warehouseAreaIds);

    void saveAll(List<WarehouseLogic> warehouseLogics);
}
