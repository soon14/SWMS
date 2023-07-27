package com.swms.wms.basic.warehouse.domain.service;

import com.swms.wms.basic.warehouse.domain.entity.Location;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseAreaGroup;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseLogic;

import java.util.List;

public interface WarehouseService {

    boolean validate(WarehouseAreaGroup warehouseAreaGroup);

    List<Location> getLocationsByWarehouseAreaGroup(WarehouseAreaGroup warehouseAreaGroup);

    List<Location> getLocationsByWarehouseArea(WarehouseArea warehouseArea);

    List<Location> getLocationsByWarehouseLogic(WarehouseLogic warehouseLogic);

    List<WarehouseArea> getWarehouseAreasByWarehouseAreaGroup(WarehouseAreaGroup warehouseAreaGroup);

    List<WarehouseLogic> getWarehouseLogicsByWarehouseAreaIds(List<Long> warehouseAreaIds);
}
