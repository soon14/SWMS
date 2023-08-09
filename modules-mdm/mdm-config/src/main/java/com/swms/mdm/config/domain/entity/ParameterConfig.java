package com.swms.mdm.config.domain.entity;

import com.swms.mdm.api.config.constants.ConfigApplyModuleEnum;
import com.swms.mdm.api.config.constants.ConfigTypeEnum;
import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ParameterConfig {

    private Long id;
    private ParameterCodeEnum code;
    private String name;

    private ConfigApplyModuleEnum configApplyModule;
    private ConfigTypeEnum configType;

    private Object defaultValue;

    private boolean enable;

    private String description;
    private String remark;

    private Long version;

    @NotEmpty
    private List<ParameterConfigDTO.ParameterConfigCondition> parameterConfigConditions;

    public String getDefaultValue() {
        return defaultValue.toString();
    }
}
