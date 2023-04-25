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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class ParameterConfigApiTest extends BaseTest {

    @Autowired
    private IParameterConfigApi iParameterConfigApi;

    @Autowired
    private ParameterConfigRepository parameterConfigRepository;

    @Test
    void testSave() {
        ParameterConfigDTO parameterConfig = new ParameterConfigDTO();
        parameterConfig.setCode("test");
        parameterConfig.setName("测试");
        parameterConfig.setConfigType(ConfigTypeEnum.BOOLEAN);
        parameterConfig.setConfigApplyModule(ConfigApplyModuleEnum.INBOUND);
        parameterConfig.setEnable(true);
        parameterConfig.setConfigApplyObject(ConfigApplyObjectEnum.OWNER);
        parameterConfig.setDefaultValue("false");
        parameterConfig.setDescription("测试");
        iParameterConfigApi.save(parameterConfig);

        ParameterConfig test = parameterConfigRepository.findByCode("test");
        Assertions.assertNotNull(test);
    }

    @Test
    void testUpdate() {
        ParameterConfigDTO parameterConfig = new ParameterConfigDTO();
        parameterConfig.setCode("test");
        parameterConfig.setName("测试11");
        parameterConfig.setConfigType(ConfigTypeEnum.BOOLEAN);
        parameterConfig.setConfigApplyModule(ConfigApplyModuleEnum.INBOUND);
        parameterConfig.setEnable(true);
        parameterConfig.setConfigApplyObject(ConfigApplyObjectEnum.OWNER);
        parameterConfig.setDefaultValue("false");
        parameterConfig.setDescription("测试");
        parameterConfig.setVersion(0L);
        iParameterConfigApi.save(parameterConfig);

        ParameterConfig test = parameterConfigRepository.findByCode("test");
        Assertions.assertNotNull(test);
        Assertions.assertEquals("测试11", test.getName());
    }

    @Test
    void testGetParameterConfig() {
        List<ParameterConfigDTO> parameterConfigs = iParameterConfigApi.getParameterConfig(ConfigApplyObjectEnum.OWNER);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(parameterConfigs));
    }
}
