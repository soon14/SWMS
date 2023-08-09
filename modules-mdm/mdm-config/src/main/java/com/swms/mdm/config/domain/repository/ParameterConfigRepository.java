package com.swms.mdm.config.domain.repository;

import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.config.domain.entity.ParameterConfig;

public interface ParameterConfigRepository {

    void save(ParameterConfig parameterConfig);

    ParameterConfig findByCode(ParameterCodeEnum code);

    ParameterConfig findById(Long id);
}
