package com.swms.wms.basic.warehouse.domain.entity;

import lombok.Data;

@Data
public class Aisle {

    private Long id;
    // unique identifier
    private String aisleCode;

    private String warehouseAreaCode;
}
