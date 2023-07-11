package com.swms.wms.api.basic.dto;

import com.swms.wms.api.basic.constants.WarehouseAreaTypeEnum;
import com.swms.wms.api.basic.constants.WarehouseAreaUseEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseAreaDTO {

    private Long id;

    @NotEmpty
    private String warehouseAreaCode;
    @NotEmpty
    private String warehouseCode;

    @NotEmpty
    private String warehouseAreaName;

    @NotEmpty
    private String warehouseGroupCode;

    @NotNull
    private WarehouseAreaTypeEnum warehouseAreaType;

    @NotNull
    private WarehouseAreaUseEnum warehouseAreaUse;

    private int level;
    private int temperatureLimit;
    private int wetLimit;

    private boolean deleted;
    private boolean enable;

    private String remark;

    private long version;
}
