package com.swms.mdm.config.application;

import com.swms.mdm.api.config.IParameterConfigApi;
import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.config.domain.entity.ParameterConfig;
import com.swms.mdm.config.domain.repository.ParameterConfigRepository;
import com.swms.mdm.config.domain.transfer.ParameterConfigTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Validated
@Service
@Slf4j
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
    public boolean getBooleanParameter(ParameterCodeEnum parameterCode, Object... conditionValues) {
        Object parameter = getObjectParameter(parameterCode, conditionValues);

        if (parameter == null) {
            return false;
        }

        return Boolean.parseBoolean(parameter.toString());
    }

    @Override
    public String getStringParameter(ParameterCodeEnum parameterCode, Object... conditionValues) {
        Object parameter = getObjectParameter(parameterCode, conditionValues);

        if (parameter == null) {
            return "";
        }

        return parameter.toString();
    }

    @Override
    public Integer getIntegerParameter(ParameterCodeEnum parameterCode, Object... conditionValues) {
        Object parameter = getObjectParameter(parameterCode, conditionValues);

        if (parameter == null) {
            return 0;
        }

        return Integer.parseInt(parameter.toString());
    }

    private Object getObjectParameter(ParameterCodeEnum parameterCode, Object... conditionValues) {

        ParameterConfig parameterConfig = parameterConfigRepository.findByCode(parameterCode);
        if (parameterConfig == null || !parameterConfig.isEnable()) {
            log.warn("parameter code: {} is not defined or is defined but not enable, then return null.",
                parameterCode.getValue());
            return null;
        }

        ParameterConfigDTO.ParameterConfigCondition parameterConfigCondition = parameterConfig
            .getParameterConfigConditions().stream().filter(condition -> {
                List<ParameterConfigDTO.ConditionObject> conditionObjects = condition.getConditionObjects();
                if (conditionObjects.size() != conditionValues.length) {
                    return false;
                }
                int index = 0;
                boolean match = true;
                for (ParameterConfigDTO.ConditionObject v : conditionObjects) {
                    if (!Objects.equals(v.getConditionValue(), conditionValues[index++])) {
                        match = false;
                        break;
                    }
                }
                return match;
            }).findFirst().orElse(null);

        if (parameterConfigCondition == null) {
            log.warn("parameter code: {} with parameters: {} can't match any conditions, then return default config: {}.",
                parameterCode, conditionValues, parameterConfig.getDefaultValue());
            return parameterConfig.getDefaultValue();
        }

        return parameterConfigCondition.getValue();
    }
}
