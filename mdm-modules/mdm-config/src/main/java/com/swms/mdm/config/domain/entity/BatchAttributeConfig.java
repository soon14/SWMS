package com.swms.mdm.config.domain.entity;


import lombok.Data;

import java.util.List;

@Data
public class BatchAttributeConfig {

    private Long id;

    private String batchAttributeCode;
    private String batchAttributeName;

    private String ownerCode;
    private String skuFirstCategory;

    private boolean enable;

    private List<BatchAttributeFieldConfig> batchAttributeFieldConfigs;

    @Data
    public static class BatchAttributeFieldConfig {
        private String fieldCode;
        private String fieldName;
        private boolean enable;
        private boolean required;
        private boolean keyAttribute;
        private String format;
    }
}
