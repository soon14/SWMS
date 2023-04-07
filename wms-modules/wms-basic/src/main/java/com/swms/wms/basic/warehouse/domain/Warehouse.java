package com.swms.wms.basic.warehouse.domain;

import lombok.Data;

@Data
public class Warehouse {

    private Long id;
    // unique identifier
    private String warehouseCode;

    private String warehouseName;
}
