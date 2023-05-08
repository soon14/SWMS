package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.wms.api.basic.constants.WarehouseAreaTypeEnum;
import lombok.Data;

@Data
public class WarehouseArea {

    private Long id;

    // union unique identifier
    private String warehouseAreaCode;
    private String warehouseCode;

    private String warehouseAreaName;

    private String warehouseGroupCode;

    private WarehouseAreaTypeEnum warehouseAreaType;

    private boolean deleted;
    private boolean enable;
}
