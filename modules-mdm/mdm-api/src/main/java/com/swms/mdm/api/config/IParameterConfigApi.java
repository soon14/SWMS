package com.swms.mdm.api.config;

import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IParameterConfigApi {

    void save(@Valid ParameterConfigDTO parameterConfigDTO);

    void update(@Valid ParameterConfigDTO parameterConfigDTO);

    List<ParameterConfigDTO> getParameterConfig(@NotNull ConfigApplyObjectEnum configApplyObject);
}
