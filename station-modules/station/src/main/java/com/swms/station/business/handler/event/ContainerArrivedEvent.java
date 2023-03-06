package com.swms.station.business.handler.event;

import lombok.Data;

@Data
public class ContainerArrivedEvent {
    private String containerCode;
    private String face;
    private String stationCode;
    private String robotCode;
    private String robotType;
    private String locationCode;

    private String groupCode;
}
