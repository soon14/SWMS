package com.swms.mdm.main.data.domain.repository;

import com.swms.mdm.main.data.domain.entity.WarehouseMainData;

import java.util.Collection;

public interface WarehouseMainDataRepository {

    void save(WarehouseMainData warehouse);

    WarehouseMainData findById(Long id);

    WarehouseMainData getWarehouse(String warehouseCode);

    Collection<WarehouseMainData> getWarehouses(Collection<String> warehouseCodes);

}
