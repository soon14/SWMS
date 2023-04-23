package com.swms.mdm.config.domain.entity;

import com.swms.mdm.api.config.constants.ConfigApplyModuleEnum;
import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.api.config.constants.ConfigTypeEnum;
import lombok.Data;

@Data
public class ParameterConfig {

    private Long id;
    private String code;
    private String name;

    private ConfigApplyObjectEnum configApplyObject;
    private ConfigApplyModuleEnum configApplyModule;
    private ConfigTypeEnum configType;

    private boolean enable;

    private Object defaultValue;

    private String description;
    private String remark;
}
