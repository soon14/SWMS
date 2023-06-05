package com.swms.mdm.query.main.data.vo;

import com.swms.mdm.api.main.data.constants.WarehouseAttrTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseBusinessTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseLevelEnum;
import com.swms.mdm.api.main.data.constants.WarehouseStructureTypeEnum;
import com.swms.mdm.api.main.data.constants.WarehouseTypeEnum;
import lombok.Data;

@Data
public class WarehouseMainDataVO {

    private Long id;

    private String warehouseCode;
    private String warehouseName;
    private WarehouseTypeEnum warehouseType;
    private WarehouseAttrTypeEnum warehouseAttrType;
    private WarehouseLevelEnum warehouseLevel;
    private String warehouseLabel;
    private WarehouseBusinessTypeEnum businessType;
    private WarehouseStructureTypeEnum structureType;
    private Integer area;
    private Integer height;
    private boolean virtualWarehouse;
    private String name;
    private String tel;
    private String mail;
    private String fax;
    private String country;
    private String province;
    private String city;
    private String district;
    private String address;
}
