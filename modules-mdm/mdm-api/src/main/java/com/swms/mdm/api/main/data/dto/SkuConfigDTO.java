package com.swms.mdm.api.main.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
