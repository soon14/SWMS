package com.swms.wms.api.basic.dto;

import lombok.Data;

@Data
public class WarehouseLogicDTO {
    private Long id;

    // union unique identifier
    private String warehouseLogicCode;
    private String warehouseAreaCode;

    private String warehouseLogicName;

    private String warehouseCode;

    private String remark;

    private long version;
}
