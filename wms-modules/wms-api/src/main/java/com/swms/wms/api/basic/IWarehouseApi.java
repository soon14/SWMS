package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseDTO;

public interface IWarehouseApi {

    void createWarehouse(WarehouseDTO warehouseDTO);

    void updateWarehouse(WarehouseDTO warehouseDTO);

    void enableWarehouse(Long id);

    void disableWarehouse(Long id);

    void deleteWarehouse(WarehouseDTO warehouseDTO);

    WarehouseDTO getWarehouse(String warehouseCode);
}
