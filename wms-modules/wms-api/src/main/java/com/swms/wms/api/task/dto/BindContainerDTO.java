package com.swms.wms.api.task.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BindContainerDTO {
    @NotEmpty
    private String putWallSlotCode;
    @NotEmpty
    private String containerCode;
}
