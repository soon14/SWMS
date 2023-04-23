package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseConfigDTO;

public interface IWarehouseConfigApi {

    void save(WarehouseConfigDTO warehouseConfigDTO);

    void update(WarehouseConfigDTO warehouseConfigDTO);

    WarehouseConfigDTO getWarehouseConfig(String warehouseCode);
}
