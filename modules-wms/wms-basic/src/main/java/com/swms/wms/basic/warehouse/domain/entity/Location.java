package com.swms.wms.basic.warehouse.domain.entity;

import com.swms.common.utils.base.UpdateUserDTO;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.BasicErrorDescEnum;
import com.swms.wms.api.basic.constants.LocationStatusEnum;
import com.swms.wms.api.basic.constants.LocationTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Location extends UpdateUserDTO {

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

    private long version;


    public void delete() {
        if (occupied) {
            throw WmsException.throwWmsException(BasicErrorDescEnum.LOCATION_CONTAINS_STOCK);
        }
    }

    public void enable() {
        this.setLocationStatus(LocationStatusEnum.PUT_AWAY_PUT_DOWN);
    }

    public void disable() {
        this.setLocationStatus(LocationStatusEnum.NONE);
    }
}
