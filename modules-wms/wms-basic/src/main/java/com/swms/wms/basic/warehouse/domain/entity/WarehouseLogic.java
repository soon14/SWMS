package com.swms.wms.basic.warehouse.domain.entity;

import lombok.Data;

@Data
public class WarehouseLogic {

    private Long id;

    private String warehouseCode;
    private String warehouseGroupCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;
    private String warehouseLogicName;

    private String remark;

    private boolean deleted;
    private boolean enable;

    private long version;
}
