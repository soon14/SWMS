package com.swms.wms.basic.warehouse.domain.entity;

import lombok.Data;

@Data
public class WarehouseAreaGroup {

    private Long id;

    // unique identifier
    private String warehouseAreaGroupCode;
    private String warehouseCode;
}
