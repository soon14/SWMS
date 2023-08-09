package com.swms.mdm.api.config.dto;

import com.swms.mdm.api.config.constants.ConfigApplyModuleEnum;
import com.swms.mdm.api.config.constants.ConfigTypeEnum;
import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ParameterConfigDTO {

    private Long id;

    @NotNull
    private ParameterCodeEnum code;
    @NotEmpty
    private String name;

    @NotNull
    private ConfigApplyModuleEnum configApplyModule;
    @NotNull
    private ConfigTypeEnum configType;

    @NotNull
    private Object defaultValue;

    private boolean enable;

    @NotEmpty
    private String description;
    private String remark;

    private Long version;

    @NotEmpty
    private List<ParameterConfigCondition> parameterConfigConditions;

    @Data
    public static class ParameterConfigCondition {

        @NotEmpty
        private List<ConditionObject> conditionObjects;

        @NotNull
        private Object value;

        private String remark;
    }

    @Data
    public static class ConditionObject {

        @NotEmpty
        private String condition;

        @NotNull
        private Object conditionValue;
    }
}
