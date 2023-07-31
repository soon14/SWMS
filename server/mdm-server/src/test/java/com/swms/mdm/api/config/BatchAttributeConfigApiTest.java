package com.swms.mdm.api.config;

import com.swms.mdm.api.BaseTest;
import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.mdm.config.domain.entity.BatchAttributeConfig;
import com.swms.mdm.config.domain.repository.BatchAttributeConfigRepository;
import com.swms.utils.utils.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class BatchAttributeConfigApiTest extends BaseTest {

    @Autowired
    private IBatchAttributeConfigApi batchAttributeConfigApi;

    @Autowired
    private BatchAttributeConfigRepository batchAttributeConfigRepository;

    final String code = "123";

    @Test
    @Order(1)
    void testSave() {
        BatchAttributeConfigDTO batchAttributeConfigDTO = ObjectUtils.getRandomObject(BatchAttributeConfigDTO.class);
        batchAttributeConfigDTO.setCode(code);
        batchAttributeConfigApi.save(batchAttributeConfigDTO);

        List<BatchAttributeConfig> batchAttributeConfigs = batchAttributeConfigRepository.findAll();
        Assertions.assertTrue(batchAttributeConfigs.size() >= 1);
        Assertions.assertTrue(batchAttributeConfigs.stream().anyMatch(batchAttributeConfig -> batchAttributeConfig.getCode().equals(code)));
    }

    @Test
    @Order(2)
    void testUpdate() {
        BatchAttributeConfig batchAttributeConfig = batchAttributeConfigRepository.findByCode(code);
        Assertions.assertNotNull(batchAttributeConfig);
        BatchAttributeConfigDTO batchAttributeConfigDTO = ObjectUtils.getRandomObject(BatchAttributeConfigDTO.class);
        batchAttributeConfigDTO.setId(batchAttributeConfig.getId());
        batchAttributeConfigDTO.setCode(batchAttributeConfig.getCode());
        batchAttributeConfigDTO.setName("测试11");
        batchAttributeConfigDTO.setVersion(batchAttributeConfig.getVersion());
        batchAttributeConfigApi.update(batchAttributeConfigDTO);

        batchAttributeConfig = batchAttributeConfigRepository.findByCode(code);
        Assertions.assertEquals("测试11", batchAttributeConfig.getName());
    }

    @Test
    @Order(3)
    void testGetBatchAttributeConfig() {
        BatchAttributeConfig batchAttributeConfig = batchAttributeConfigRepository.findByCode(code);
        BatchAttributeConfigDTO batchAttributeConfigDTO = batchAttributeConfigApi.getByOwnerAndSkuFirstCategory(
            batchAttributeConfig.getOwnerCode(), batchAttributeConfig.getSkuFirstCategory()
        );
        Assertions.assertNotNull(batchAttributeConfigDTO);
    }
}
