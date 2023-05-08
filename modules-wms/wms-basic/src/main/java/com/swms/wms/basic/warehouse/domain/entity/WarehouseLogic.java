package com.swms.wms.basic.warehouse.domain.entity;

import lombok.Data;

@Data
public class WarehouseLogic {

    private Long id;

    // union unique identifier
    private String warehouseLogicCode;
    private String warehouseAreaCode;

    private String warehouseLogicName;

    private String warehouseCode;
}
