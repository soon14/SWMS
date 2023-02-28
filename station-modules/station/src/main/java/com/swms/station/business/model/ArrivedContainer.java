package com.swms.station.business.model;

import lombok.Data;

import java.util.List;

@Data
public class ArrivedContainer {

    private String containerCode;
    private String containerType;
    private String face;
    private Integer rotationAngle;
    private List<Slot> slots;
    private String locationCode;

    private boolean empty;

    /**
     * 0: unprocessed ,1: processing , 2: processed
     */
    private Integer processStatus;
}
