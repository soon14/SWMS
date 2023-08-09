package com.swms.mdm.api.config;

import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public interface IParameterConfigApi {

    void save(@Valid ParameterConfigDTO parameterConfigDTO);

    void update(@Valid ParameterConfigDTO parameterConfigDTO);

    boolean getBooleanParameter(@NotNull ParameterCodeEnum parameterCode, @NotEmpty Object... conditionValues);

    String getStringParameter(@NotNull ParameterCodeEnum parameterCode, @NotEmpty Object... conditionValues);

    Integer getIntegerParameter(@NotNull ParameterCodeEnum parameterCode, @NotEmpty Object... conditionValues);
}
