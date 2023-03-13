package com.swms.wms.api.warehouse.dto;

import lombok.Data;

@Data
public class WorkLocationSlotDTO {

    private String workLocationCode;
    private String groupCode;

    private String slotCode;
    private Integer level;
    private Integer bay;
    private boolean enable;
}
