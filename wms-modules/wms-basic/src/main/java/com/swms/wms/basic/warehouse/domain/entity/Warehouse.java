package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.mdm.api.config.dto.WarehouseConfigDTO;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import lombok.Data;

@Data
public class Warehouse {

    private Long id;
    private WarehouseMainDataDTO warehouseMainData;
    private WarehouseConfigDTO warehouseConfig;

}
