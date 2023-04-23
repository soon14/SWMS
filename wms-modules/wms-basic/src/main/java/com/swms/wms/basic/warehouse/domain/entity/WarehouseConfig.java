package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.mdm.api.config.dto.WarehouseMainDataConfigDTO;
import lombok.Data;

@Data
public class WarehouseConfig {

    private Long id;
    private String warehouseCode;
    private WarehouseMainDataConfigDTO warehouseMainDataConfig;
}
