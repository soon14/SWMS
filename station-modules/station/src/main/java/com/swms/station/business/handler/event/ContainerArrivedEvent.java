package com.swms.station.business.handler.event;

import com.swms.wms.api.warehouse.constants.WorkLocationTypeEnum;
import lombok.Data;

@Data
public class ContainerArrivedEvent {
    private String containerCode;
    private String face;
    private String stationCode;
    private String robotCode;
    private String robotType;
    private Integer level;
    private Integer bay;
    private String locationCode;

    private String workLocationCode;
    private WorkLocationTypeEnum workLocationType;

    private String groupCode;
}
