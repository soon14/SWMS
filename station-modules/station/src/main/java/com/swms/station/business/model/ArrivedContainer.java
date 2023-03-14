package com.swms.station.business.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * ArrivedContainer wouldn't pass to WMS system , it will be restored to redis and memory .
 * if Station system crashes then it will be got from redis when the system is restarted.
 */
@Data
@Builder
public class ArrivedContainer {

    private String containerCode;
    private String containerType;
    private String face;
    private Integer rotationAngle;
    private String locationCode;
//    private List<Slot> slots;

    // a set of locations that the container can be put into.
    // it is a logical definition.
    // like robotCode is the groupCode of robot, cache shelf code is the groupCode of cache shelf.
    private String groupCode;

    private String robotCode;
    private String robotType;

    private boolean empty;

    /**
     * 0: unprocessed ,1: processing , 2: processed
     */
    private Integer processStatus;
}
