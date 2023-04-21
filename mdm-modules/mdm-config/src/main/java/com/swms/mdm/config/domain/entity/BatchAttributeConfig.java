package com.swms.mdm.config.domain.entity;


import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class BatchAttributeConfig {

    private Long id;

    private String batchAttributeCode;
    private String batchAttributeName;

    private String ownerCode;
    private String skuFirstCategory;

    private boolean enable;

    private List<BatchAttributeConfigDTO.BatchAttributeFieldConfigDTO> batchAttributeFieldConfigs;

    public boolean match(String ownerCode, String skuFirstCategory) {
        return matchOwner(ownerCode) && matchSkuFirstCategory(skuFirstCategory);
    }

    public boolean matchOwner(String ownerCode) {
        return StringUtils.equals(this.ownerCode, "*") || StringUtils.equals(ownerCode, "*")
            || StringUtils.equals(this.ownerCode, ownerCode);
    }

    public boolean matchSkuFirstCategory(String skuFirstCategory) {
        return StringUtils.equals(this.skuFirstCategory, "*") || StringUtils.equals(skuFirstCategory, "*") ||
            StringUtils.equals(this.skuFirstCategory, skuFirstCategory);
    }
}
