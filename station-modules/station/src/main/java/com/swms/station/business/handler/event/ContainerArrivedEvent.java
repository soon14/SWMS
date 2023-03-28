package com.swms.station.business.handler.event;

import com.swms.wms.api.warehouse.constants.WorkLocationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerArrivedEvent {
    private String containerCode;

    // container arrived with which face
    private String face;
    private String stationCode;
    private String robotCode;
    private String robotType;
    private Integer level;
    private Integer bay;
    private String locationCode;

    // container forward face
    private String forwardFace;

    private String workLocationCode;
    private WorkLocationTypeEnum workLocationType;

    private String groupCode;

    private Map<String, Object> containerAttributes;
}
