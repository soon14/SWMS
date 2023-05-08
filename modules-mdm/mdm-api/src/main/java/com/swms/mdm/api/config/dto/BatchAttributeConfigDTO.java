package com.swms.mdm.api.config.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BatchAttributeConfigDTO {

    private Long id;

    @NotEmpty
    private String code;
    @NotEmpty
    private String name;

    private String ownerCode;
    private String skuFirstCategory;

    private boolean enable;

    private Long version;

    @NotEmpty
    private List<BatchAttributeFieldConfigDTO> batchAttributeFieldConfigs;

    @Data
    public static class BatchAttributeFieldConfigDTO {

        @NotEmpty
        private String fieldCode;
        @NotEmpty
        private String fieldName;
        @NotEmpty
        private String format;
        private boolean enable;
        private boolean required;
        private boolean keyAttribute;
    }
}
