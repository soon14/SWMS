package com.swms.wms.api.task.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SealContainerDTO {

    @NotEmpty
    private String stationCode;
    @NotEmpty
    private String putWallSlotCode;
    @NotEmpty
    private String transferContainerCode;
}
