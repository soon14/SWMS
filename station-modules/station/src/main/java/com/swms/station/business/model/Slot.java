package com.swms.station.business.model;

import lombok.Data;

@Data
public class Slot {
    private String slotCode;
    private Integer level;
    private Integer bay;
    private boolean enable;

    private ArrivedContainer arrivedContainer;
}
