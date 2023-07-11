package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.wms.api.basic.constants.WarehouseAreaTypeEnum;
import com.swms.wms.api.basic.constants.WarehouseAreaUseEnum;
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

    private WarehouseAreaUseEnum warehouseAreaUse;

    private String remark;

    private int level;
    private int temperatureLimit;
    private int wetLimit;

    private boolean deleted;
    private boolean enable;

    private long version;
}
