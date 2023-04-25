package com.swms.mdm.api.config.dto;

import com.swms.mdm.api.config.constants.ConfigApplyModuleEnum;
import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.api.config.constants.ConfigTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParameterConfigDTO {

    private Long id;

    @NotEmpty
    private String code;
    @NotEmpty
    private String name;

    @NotNull
    private ConfigApplyObjectEnum configApplyObject;
    @NotNull
    private ConfigApplyModuleEnum configApplyModule;
    @NotNull
    private ConfigTypeEnum configType;

    private boolean enable;

    @NotEmpty
    private String defaultValue;

    @NotEmpty
    private String description;
    private String remark;
}
