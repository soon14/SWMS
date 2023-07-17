package com.swms.wms.api.basic.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreatePutWallDTO {

    @NotEmpty
    private String warehouseCode;

    @NotEmpty
    private Long workStationId;
    @NotEmpty
    private String putWallCode;
    @NotEmpty
    private String putWallName;
    @NotEmpty
    private String containerSpecCode;
}
