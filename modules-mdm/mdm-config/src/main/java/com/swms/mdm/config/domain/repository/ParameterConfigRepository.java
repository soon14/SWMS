package com.swms.mdm.config.domain.repository;

import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.config.domain.entity.ParameterConfig;

import java.util.List;

public interface ParameterConfigRepository {

    void save(ParameterConfig parameterConfig);

    List<ParameterConfig> findByConfigApplyObject(ConfigApplyObjectEnum configApplyObject);

    ParameterConfig findByCode(String code);

    ParameterConfig findById(Long id);
}
