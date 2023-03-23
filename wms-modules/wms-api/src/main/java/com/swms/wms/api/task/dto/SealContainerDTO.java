package com.swms.wms.api.task.dto;

import lombok.Data;

@Data
public class SealContainerDTO {
    private String stationCode;
    private String putWallSlotCode;
    private String containerCode;
}
