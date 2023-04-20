package com.swms.mdm.api.main.data.dto;

import lombok.Data;

@Data
public class SkuConfigDTO {

    private boolean enableSn;
    private boolean enableEffective;

    private Integer shelfLife;
    private Integer effectiveDays;

    private String barcodeRuleCode;

    private String heat;
    private boolean calculateHeat;

    private boolean noBarcode;

}
