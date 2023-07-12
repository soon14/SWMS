package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.wms.api.basic.constants.LocationTypeEnum;
import lombok.Data;

@Data
public class Location {

    private Long id;
    // unique identifier
    private String locationCode;
    private String aisleCode;
    private String shelfCode;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;

    private LocationTypeEnum locationType;

    private String heat;
    private boolean locked;
    private boolean occupied;

    private long version;
}
