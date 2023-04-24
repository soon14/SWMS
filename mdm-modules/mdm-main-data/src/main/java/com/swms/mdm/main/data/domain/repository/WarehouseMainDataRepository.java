package com.swms.mdm.main.data.domain.repository;

import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.mdm.main.data.domain.entity.WarehouseMainData;

public interface WarehouseMainDataRepository {

    void save(WarehouseMainData warehouse);

    WarehouseMainData findById(Long id);

    WarehouseMainData getWarehouse(String warehouseCode);
}
