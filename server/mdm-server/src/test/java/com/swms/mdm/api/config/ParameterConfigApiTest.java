package com.swms.mdm.api.config;

import com.swms.mdm.api.BaseTest;
import com.swms.mdm.api.config.constants.ConfigApplyModuleEnum;
import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.api.config.constants.ConfigTypeEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.config.domain.entity.ParameterConfig;
import com.swms.mdm.config.domain.repository.ParameterConfigRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class ParameterConfigApiTest extends BaseTest {

    @Autowired
    private IParameterConfigApi iParameterConfigApi;

    @Autowired
    private ParameterConfigRepository parameterConfigRepository;

    final String code = "12345";

    @Test
    @Order(1)
    void testSave() {
        ParameterConfigDTO parameterConfig = new ParameterConfigDTO();
        parameterConfig.setCode(code);
        parameterConfig.setName("测试");
        parameterConfig.setConfigType(ConfigTypeEnum.BOOLEAN);
        parameterConfig.setConfigApplyModule(ConfigApplyModuleEnum.INBOUND);
        parameterConfig.setEnable(true);
        parameterConfig.setConfigApplyObject(ConfigApplyObjectEnum.OWNER);
        parameterConfig.setDefaultValue("false");
        parameterConfig.setDescription("测试");
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

    @Test
    @Order(3)
    void testGetParameterConfig() {
        List<ParameterConfigDTO> parameterConfigs = iParameterConfigApi.getParameterConfig(ConfigApplyObjectEnum.OWNER);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(parameterConfigs));
    }
}
