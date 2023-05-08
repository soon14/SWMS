package com.swms.wms.api.basic.dto;

import com.swms.mdm.api.config.dto.WarehouseMainDataConfigDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class WarehouseConfigDTO {

    @NotEmpty
    private String warehouseCode;
    private WarehouseMainDataConfigDTO warehouseMainDataConfig;
}
