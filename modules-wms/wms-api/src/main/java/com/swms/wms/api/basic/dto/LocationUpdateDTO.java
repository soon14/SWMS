package com.swms.wms.api.basic.dto;

import com.swms.wms.api.basic.constants.LocationStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class LocationUpdateDTO {

    @NotEmpty
    private List<Long> locationIds;

    @NotNull
    private Long warehouseLogicId;

    @NotNull
    private String heat;

    @NotNull
    private LocationStatusEnum locationStatus;
}
