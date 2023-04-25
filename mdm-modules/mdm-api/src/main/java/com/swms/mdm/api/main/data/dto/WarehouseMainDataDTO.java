package com.swms.mdm.api.main.data.dto;

import com.swms.mdm.api.main.data.constants.WarehouseAttrTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseBusinessTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseLevelEnum;
import com.swms.mdm.api.main.data.constants.WarehouseStructureTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseTypeEnum;
import lombok.Data;

@Data
public class WarehouseMainDataDTO {

    private Long id;
    private String warehouseCode;
    private String warehouseName;

    private WarehouseTypeEnum warehouseType;
    private WarehouseAttrTypeEnum warehouseAttrType;
    private WarehouseLevelEnum warehouseLevel;

    private String warehouseLabel;

    private WarehouseBusinessTypeEnum businessType;

    private WarehouseStructureTypeEnum structureType;
    private String area;
    private Integer height;
    private boolean virtualWarehouse;

    private ContactorDTO contactor;

    private AddressDTO address;

    private Long version;
}
