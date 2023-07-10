package com.swms.mdm.api.main.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuPackageDTO {

    private List<SkuPackageDetail> skuPackageDetails;

    @Data
    public static class SkuPackageDetail {

        @NotNull
        private Integer level;

        @NotEmpty
        private String packageCode;

        @NotEmpty
        private String unit;
        private boolean enableSplit;

        @Min(value = 0, message = "height must be greater than or equal to 0")
        @NotNull
        private Long height;

        @Min(value = 0, message = "width must be greater than or equal to 0")
        @NotNull
        private Long width;

        @Min(value = 0, message = "length must be greater than or equal to 0")
        @NotNull
        private Long length;

        @Min(value = 0, message = "weight must be greater than or equal to 0")
        @NotNull
        private Integer weight;
    }
}
