package com.swms.mdm.api.main.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VolumeDTO {

    @Min(value = 0, message = "volume must be greater than or equal to 0")
    @NotNull
    private Long volume;

    @Min(value = 0, message = "height must be greater than or equal to 0")
    @NotNull
    private Long height;

    @Min(value = 0, message = "width must be greater than or equal to 0")
    @NotNull
    private Long width;

    @Min(value = 0, message = "length must be greater than or equal to 0")
    @NotNull
    private Long length;
}
