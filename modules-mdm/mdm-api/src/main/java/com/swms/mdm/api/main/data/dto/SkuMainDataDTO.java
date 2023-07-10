package com.swms.mdm.api.main.data.dto;

import lombok.Data;

@Data
public class SkuMainDataDTO {
    private Long id;

    private String skuCode;
    private String ownerCode;
    private String skuName;
    private String style;
    private String color;
    private String size;
    private String brand;

    private WeightDTO weight;
    private VolumeDTO volumeDTO;

    private SkuAttributeDTO skuAttribute;

    private SkuConfigDTO skuConfig;
    private SkuPackageDTO skuPackage;
    private BarcodeDTO skuBarcode;

    private Long version;
}
