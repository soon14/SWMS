package com.swms.wms.basic.work_station.domain.entity;

import lombok.Data;

@Data
public class WorkLocationSlot {
    private Long id;
    private String workLocationCode;
    private String groupCode;

    private String slotCode;
    private Integer level;
    private Integer bay;
    private boolean enable;
}
