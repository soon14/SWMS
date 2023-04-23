package com.swms.mdm.api.config;

import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;

import java.util.List;

public interface IParameterConfigApi {

    void save(ParameterConfigDTO parameterConfigDTO);

    void update(ParameterConfigDTO parameterConfigDTO);

    List<ParameterConfigDTO> getParameterConfig(ConfigApplyObjectEnum configApplyObject);
}
