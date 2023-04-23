package com.swms.mdm.config.application;

import com.swms.mdm.api.config.IParameterConfigApi;
import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.config.domain.repository.ParameterConfigRepository;
import com.swms.mdm.config.domain.transfer.ParameterConfigTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterConfigApplicationImpl implements IParameterConfigApi {

    @Autowired
    private ParameterConfigRepository parameterConfigRepository;

    @Autowired
    private ParameterConfigTransfer parameterConfigTransfer;

    @Override
    public void save(ParameterConfigDTO parameterConfigDTO) {
        parameterConfigRepository.save(parameterConfigTransfer.toParameterConfig(parameterConfigDTO));
    }

    @Override
    public void update(ParameterConfigDTO parameterConfigDTO) {
        parameterConfigRepository.save(parameterConfigTransfer.toParameterConfig(parameterConfigDTO));
    }

    @Override
    public List<ParameterConfigDTO> getParameterConfig(ConfigApplyObjectEnum configApplyObject) {
        return parameterConfigTransfer.toParameterConfigDTOS(parameterConfigRepository.findByConfigApplyObject(configApplyObject));
    }
}
