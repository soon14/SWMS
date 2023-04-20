package com.swms.mdm.main.data.domain.entity;

import com.swms.mdm.api.config.dto.WarehouseConfigDTO;
import com.swms.mdm.api.main.data.dto.AddressDTO;
import com.swms.mdm.api.main.data.dto.ContactorDTO;
import com.swms.mdm.api.main.data.dto.WarehouseAttrTypeEnum;
import com.swms.mdm.api.main.data.dto.WarehouseBusinessTypeEnum;
import com.swms.mdm.api.main.data.dto.WarehouseLevelEnum;
import com.swms.mdm.api.main.data.dto.WarehouseStructureTypeEnum;
import com.swms.mdm.api.main.data.dto.WarehouseTypeEnum;
import lombok.Data;

@Data
public class Warehouse {

    private Long id;
    // unique identifier
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

    private boolean deleted;
    private boolean enable;

    private Long version;

    private WarehouseConfigDTO warehouseConfig;
}

