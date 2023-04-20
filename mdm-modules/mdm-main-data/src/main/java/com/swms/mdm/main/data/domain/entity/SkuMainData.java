package com.swms.mdm.main.data.domain.entity;

import com.swms.mdm.api.main.data.dto.SkuAttributeDTO;
import com.swms.mdm.api.main.data.dto.SkuConfigDTO;
import com.swms.mdm.api.main.data.dto.VolumeDTO;
import com.swms.mdm.api.main.data.dto.WeightDTO;
import lombok.Data;

@Data
public class SkuMainData {

    private Long id;

    /**
     * skuCode + ownerCode union unique identifier
     */
    private String skuCode;
    private String ownerCode;
    private String skuName;
    private String style;
    private String color;
    private String size;
    private String brand;

    private WeightDTO weight;
    private VolumeDTO volume;

    private SkuAttributeDTO skuAttribute;
    private SkuConfigDTO skuConfig;

    private Long version;
}
