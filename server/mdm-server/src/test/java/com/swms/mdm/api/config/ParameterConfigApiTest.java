package com.swms.mdm.api.config;

import com.swms.common.utils.utils.ObjectUtils;
import com.swms.mdm.api.BaseTest;
import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.config.domain.entity.ParameterConfig;
import com.swms.mdm.config.domain.repository.ParameterConfigRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

class ParameterConfigApiTest extends BaseTest {

    @Autowired
    private IParameterConfigApi iParameterConfigApi;

    @Autowired
    private ParameterConfigRepository parameterConfigRepository;

    final ParameterCodeEnum code = ParameterCodeEnum.INBOUND_ALLOW_MULTIPLE_ARRIVALS;

    @Test
    @Order(1)
    void testSave() {
        ParameterConfigDTO parameterConfig = ObjectUtils.getRandomObjectIgnoreFields(ParameterConfigDTO.class, "id", "version");
        parameterConfig.setCode(code);
        parameterConfig.setDefaultValue(false);
        iParameterConfigApi.save(parameterConfig);

        ParameterConfig test = parameterConfigRepository.findByCode(code);
        Assertions.assertNotNull(test);
    }

    @Test
    @Order(2)
    void testUpdate() {
        ParameterConfig parameterConfig = parameterConfigRepository.findByCode(code);

        ParameterConfigDTO parameterConfigDTO = new ParameterConfigDTO();
        BeanUtils.copyProperties(parameterConfig, parameterConfigDTO);
        parameterConfigDTO.setName("测试22");
        iParameterConfigApi.save(parameterConfigDTO);

        parameterConfig = parameterConfigRepository.findByCode(code);
        Assertions.assertNotNull(parameterConfig);
        Assertions.assertEquals("测试22", parameterConfig.getName());
    }
}
