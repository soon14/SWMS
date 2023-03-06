package com.swms.station.business.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ArrivedContainer {

    private String containerCode;
    private String containerType;
    private String face;
    private Integer rotationAngle;
    private List<Slot> slots;
    private String locationCode;

    // a set of locations that the container can be put into. it is a logical definition. like robotCode is the groupCode of robot. cache shelf code is the groupCode of cache shelf.
    private String groupCode;

    private String robotCode;
    private String robotType;

    private boolean empty;

    /**
     * 0: unprocessed ,1: processing , 2: processed
     */
    private Integer processStatus = 0;
}
