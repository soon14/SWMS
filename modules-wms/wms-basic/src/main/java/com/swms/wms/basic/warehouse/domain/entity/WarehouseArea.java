package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.wms.api.basic.constants.WarehouseAreaTypeEnum;
import com.swms.wms.api.basic.constants.WarehouseAreaUseEnum;
import lombok.Data;

@Data
public class WarehouseArea {

    private Long id;

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

    private boolean enable;
    private boolean deleted;
    private Long deleteTime;

    private long version;

    public void enable() {
        this.enable = true;
    }

    public void disable() {
        this.enable = false;
    }

    public void delete() {
        this.deleteTime = System.currentTimeMillis();
        this.deleted = true;
    }
}
