package com.swms.mdm.api.main.data.dto;

import lombok.Data;

@Data
public class SkuAttributeDTO {
    private String imageUrl;
    private String unit;

    private String skuFirstCategory;
    private String skuSecondCategory;
    private String skuThirdCategory;

    private String skuAttributeCategory;
    private String skuAttributeSubCategory;
}
