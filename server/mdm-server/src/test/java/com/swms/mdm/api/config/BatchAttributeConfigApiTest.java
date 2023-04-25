package com.swms.mdm.api.config;

import com.swms.mdm.api.BaseTest;
import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.mdm.config.domain.entity.BatchAttributeConfig;
import com.swms.mdm.config.domain.repository.BatchAttributeConfigRepository;
import com.swms.utils.utils.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class BatchAttributeConfigApiTest extends BaseTest {

    @Autowired
    private IBatchAttributeConfigApi batchAttributeConfigApi;

    @Autowired
    private BatchAttributeConfigRepository batchAttributeConfigRepository;

    @Test
    void testSave() {
        BatchAttributeConfigDTO batchAttributeConfigDTO = ObjectUtils.getRandomObject(BatchAttributeConfigDTO.class);
        batchAttributeConfigDTO.setCode("test");
        batchAttributeConfigApi.save(batchAttributeConfigDTO);

        List<BatchAttributeConfig> batchAttributeConfigs = batchAttributeConfigRepository.findAll();
        Assertions.assertTrue(batchAttributeConfigs.size() > 1);
        Assertions.assertTrue(batchAttributeConfigs.stream().anyMatch(batchAttributeConfig -> batchAttributeConfig.getCode().equals("test")));
    }

    @Test
    void testUpdate() {
        List<BatchAttributeConfig> batchAttributeConfigs = batchAttributeConfigRepository.findAll();
        BatchAttributeConfigDTO batchAttributeConfigDTO = ObjectUtils.getRandomObject(BatchAttributeConfigDTO.class);
        batchAttributeConfigDTO.setId(batchAttributeConfigs.get(0).getId());
        batchAttributeConfigDTO.setCode(batchAttributeConfigs.get(0).getCode());
        batchAttributeConfigDTO.setName("测试11");
        batchAttributeConfigDTO.setVersion(batchAttributeConfigs.get(0).getVersion());
        batchAttributeConfigApi.update(batchAttributeConfigDTO);
        Assertions.assertTrue(batchAttributeConfigs.size() > 1);
        Assertions.assertTrue(batchAttributeConfigs.stream().anyMatch(batchAttributeConfig -> batchAttributeConfig.getName().equals("测试11")));
    }

    @Test
    void testGetBatchAttributeConfig() {
        List<BatchAttributeConfig> batchAttributeConfigs = batchAttributeConfigRepository.findAll();
        BatchAttributeConfigDTO batchAttributeConfigDTO = batchAttributeConfigApi.getByOwnerAndSkuFirstCategory(
            batchAttributeConfigs.get(0).getOwnerCode(), batchAttributeConfigs.get(0).getSkuFirstCategory()
        );
        Assertions.assertNotNull(batchAttributeConfigDTO);
    }
}
