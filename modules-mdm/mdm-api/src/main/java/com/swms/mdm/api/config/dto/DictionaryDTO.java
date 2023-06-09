package com.swms.mdm.api.config.dto;

import com.swms.utils.base.BaseUserPO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictionaryDTO extends BaseUserPO {

    private Long id;
    @NotEmpty
    private String code;
    private boolean editable;

    @NotEmpty
    private String name;
    private String description;

    @NotEmpty
    private List<DictionaryItem> items;
    private long version;

    @Data
    public static class DictionaryItem {

        @NotEmpty
        private String value;
        @NotEmpty
        private String showContext;
        private int order;
        private boolean defaultItem;
        private String description;
    }


}
