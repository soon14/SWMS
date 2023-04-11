package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.wms.api.basic.constants.WarehouseAreaTypeEnum;
import lombok.Data;

@Data
public class WarehouseArea {

    private Long id;

    // unique identifier
    private String warehouseAreaCode;
    private String warehouseAreaName;

    private String warehouseCode;
    private String warehouseGroupCode;

    private WarehouseAreaTypeEnum warehouseAreaType;

    private boolean deleted;
    private boolean enable;
}
