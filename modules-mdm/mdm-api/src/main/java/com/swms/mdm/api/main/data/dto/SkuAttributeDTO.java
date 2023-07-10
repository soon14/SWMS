package com.swms.mdm.api.main.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuAttributeDTO {
    private String imageUrl;
    private String unit;

    private String skuFirstCategory;
    private String skuSecondCategory;
    private String skuThirdCategory;

    private String skuAttributeCategory;
    private String skuAttributeSubCategory;
}
