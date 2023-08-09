package com.swms.mdm.config.infrastructure.repository.impl;

import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.config.domain.entity.ParameterConfig;
import com.swms.mdm.config.domain.repository.ParameterConfigRepository;
import com.swms.mdm.config.infrastructure.persistence.mapper.ParameterConfigPORepository;
import com.swms.mdm.config.infrastructure.persistence.transfer.ParameterConfigPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ParameterConfig findByCode(ParameterCodeEnum code) {
        return parameterConfigPOTransfer.toDO(parameterConfigPORepository.findByCode(code));
    }

    @Override
    public ParameterConfig findById(Long id) {
        return parameterConfigPOTransfer.toDO(parameterConfigPORepository.findById(id).orElseThrow());
    }
}
