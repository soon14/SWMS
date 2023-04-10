package com.swms.wms.basic.warehouse.domain.entity;

import lombok.Data;

@Data
public class WarehouseLogic {

    private Long id;

    // unique identifier
    private String warehouseLogicCode;
    private String warehouseLogicName;

    private String warehouseCode;
    private String warehouseAreaCode;
}
