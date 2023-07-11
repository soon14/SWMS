package com.swms.mdm.config.infrastructure.repository.impl;

import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.config.domain.entity.ParameterConfig;
import com.swms.mdm.config.domain.repository.ParameterConfigRepository;
import com.swms.mdm.config.infrastructure.persistence.mapper.ParameterConfigPORepository;
import com.swms.mdm.config.infrastructure.persistence.transfer.ParameterConfigPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterConfigRepositoryImpl implements ParameterConfigRepository {

    @Autowired
    private ParameterConfigPORepository parameterConfigPORepository;

    @Autowired
    private ParameterConfigPOTransfer parameterConfigPOTransfer;

    @Override
    public void save(ParameterConfig parameterConfig) {
        parameterConfigPORepository.save(parameterConfigPOTransfer.toPO(parameterConfig));
    }

    @Override
    public List<ParameterConfig> findByConfigApplyObject(ConfigApplyObjectEnum configApplyObject) {
        return parameterConfigPOTransfer.toDOS(parameterConfigPORepository.findByConfigApplyObject(configApplyObject));
    }

    @Override
    public ParameterConfig findByCode(String code) {
        return parameterConfigPOTransfer.toDO(parameterConfigPORepository.findByCode(code));
    }

    @Override
    public ParameterConfig findById(Long id) {
        return parameterConfigPOTransfer.toDO(parameterConfigPORepository.findById(id).orElseThrow());
    }
}
