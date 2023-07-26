package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.BasicErrorDescEnum;
import com.swms.wms.api.basic.constants.LocationStatusEnum;
import com.swms.wms.api.basic.constants.LocationTypeEnum;
import lombok.Data;

@Data
public class Location {

    private Long id;

    private String locationCode;
    private String aisleCode;
    private String shelfCode;

    private String warehouseCode;
    private Long warehouseAreaId;
    private Long warehouseLogicId;

    private LocationTypeEnum locationType;

    private String heat;
    private boolean occupied;

    private LocationStatusEnum locationStatus;

    private boolean deleted;
    private long version;


    public void delete() {
        if (occupied) {
            throw WmsException.throwWmsException(BasicErrorDescEnum.LOCATION_CONTAINS_STOCK);
        }
        this.deleted = true;
    }
}
