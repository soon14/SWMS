package com.swms.wms.api.task.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BindContainerDTO {

    @NotNull
    private Long workStationId;
    @NotNull
    private String putWallCode;
    @NotEmpty
    private String putWallSlotCode;
    @NotEmpty
    private String containerCode;
}
