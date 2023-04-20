package com.swms.mdm.api.main.data;


import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;

public interface IWarehouseApi {

    void createWarehouse(WarehouseMainDataDTO warehouseDTO);

    void updateWarehouse(WarehouseMainDataDTO warehouseDTO);

    WarehouseMainDataDTO getWarehouse(String warehouseCode);
}
