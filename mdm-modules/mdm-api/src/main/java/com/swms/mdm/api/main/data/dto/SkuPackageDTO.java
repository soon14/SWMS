package com.swms.mdm.api.main.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class SkuPackageDTO {

    private List<SkuPackageDetail> skuPackageDetails;

    @Data
    public static class SkuPackageDetail {

        private Integer level;

        private String packageCode;
        private String unit;
        private boolean enableSplit;

        private Integer length;
        private Integer width;
        private Integer height;
        private Integer weight;
    }
}
