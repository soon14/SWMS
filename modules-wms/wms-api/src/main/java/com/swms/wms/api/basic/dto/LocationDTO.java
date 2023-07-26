package com.swms.wms.api.basic.dto;

import com.swms.wms.api.basic.constants.LocationStatusEnum;
import com.swms.wms.api.basic.constants.LocationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LocationDTO {
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

}
