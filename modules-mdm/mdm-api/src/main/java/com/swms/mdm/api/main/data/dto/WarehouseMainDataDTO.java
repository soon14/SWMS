package com.swms.mdm.api.main.data.dto;

import com.swms.mdm.api.main.data.constants.WarehouseAttrTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseBusinessTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseLevelEnum;
import com.swms.mdm.api.main.data.constants.WarehouseStructureTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WarehouseMainDataDTO {

    private Long id;

    @NotEmpty
    @Size(max = 64)
    private String warehouseCode;

    @NotEmpty
    @Size(max = 128)
    private String warehouseName;

    @NotNull
    private WarehouseTypeEnum warehouseType;
    @NotNull
    private WarehouseAttrTypeEnum warehouseAttrType;
    @NotNull
    private WarehouseLevelEnum warehouseLevel;

    @Size(max = 64)
    private String warehouseLabel;

    @NotNull
    private WarehouseBusinessTypeEnum businessType;

    @NotNull
    private WarehouseStructureTypeEnum structureType;

    @Min(0)
    private Integer area;
    @Min(0)
    private Integer height;
    private boolean virtualWarehouse;

    private ContactorDTO contactor;

    private AddressDTO address;

    private Long version;
}
