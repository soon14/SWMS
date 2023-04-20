package com.swms.mdm.api.main.data;


import com.swms.mdm.api.main.data.constants.WarehouseDTO;

public interface IWarehouseApi {

    void createWarehouse(WarehouseDTO warehouseDTO);

    void updateWarehouse(WarehouseDTO warehouseDTO);

    void enableWarehouse(Long id);

    void disableWarehouse(Long id);

    void deleteWarehouse(WarehouseDTO warehouseDTO);

    WarehouseDTO getWarehouse(String warehouseCode);
}
