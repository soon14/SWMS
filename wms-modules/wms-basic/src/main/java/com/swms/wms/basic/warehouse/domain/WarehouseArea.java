package com.swms.wms.basic.warehouse.domain;

import lombok.Data;

@Data
public class WarehouseArea {

    private Long id;

    // unique identifier
    private String warehouseAreaCode;
    private String warehouseAreaName;

    private String warehouseCode;
    private String warehouseGroupCode;
}
