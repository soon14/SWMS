package com.swms.mdm.api.config;

import com.swms.mdm.api.BaseTest;
import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import com.swms.mdm.api.config.dto.BarcodeParseRequestDTO;
import com.swms.mdm.api.config.dto.BarcodeParseResult;
import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;
import com.swms.mdm.config.domain.repository.BarcodeParseRuleRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class BarcodeParseRuleApiTest extends BaseTest {

    @Autowired
    private IBarcodeParseRuleApi iBarcodeParseRuleApi;

    @Autowired
    private BarcodeParseRuleRepository barcodeParseRuleRepository;

    final String barcodeCode = "123";

    @Test
    @Order(1)
    void testSave() {
        BarcodeParseRuleDTO barcodeParseRuleDTO = new BarcodeParseRuleDTO();
        barcodeParseRuleDTO.setBusinessFlow(BusinessFlowEnum.INBOUND);
        barcodeParseRuleDTO.setCode(barcodeCode);
        barcodeParseRuleDTO.setEnable(true);
        barcodeParseRuleDTO.setExecuteTime(ExecuteTimeEnum.SCAN_SKU);
        barcodeParseRuleDTO.setRegularExpression("(.*)");
        barcodeParseRuleDTO.setName("解析123");
        barcodeParseRuleDTO.setResultFields(Lists.newArrayList("skuCode"));
        Assertions.assertDoesNotThrow(() -> iBarcodeParseRuleApi.save(barcodeParseRuleDTO));
    }

    @Test
    @Order(2)
    void testUpdate() {
        BarcodeParseRule barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule(barcodeCode);
        BarcodeParseRuleDTO barcodeParseRuleDTO = new BarcodeParseRuleDTO();
        barcodeParseRuleDTO.setId(barcodeParseRule.getId());
        barcodeParseRuleDTO.setBusinessFlow(BusinessFlowEnum.INBOUND);
        barcodeParseRuleDTO.setCode(barcodeCode);
        barcodeParseRuleDTO.setEnable(true);
        barcodeParseRuleDTO.setExecuteTime(ExecuteTimeEnum.SCAN_SKU);
        barcodeParseRuleDTO.setRegularExpression("(.*)");
        barcodeParseRuleDTO.setName("解析1234");
        barcodeParseRuleDTO.setResultFields(Lists.newArrayList("skuCode"));
        barcodeParseRuleDTO.setVersion(barcodeParseRule.getVersion());
        Assertions.assertDoesNotThrow(() -> iBarcodeParseRuleApi.update(barcodeParseRuleDTO));
    }

    @Test
    @Order(3)
    void testDisable() {
        BarcodeParseRule barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule(barcodeCode);
        iBarcodeParseRuleApi.disable(barcodeParseRule.getId());
        barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule(barcodeCode);
        Assertions.assertFalse(barcodeParseRule.isEnable());
    }


    @Test
    @Order(4)
    void testEnable() {
        BarcodeParseRule barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule(barcodeCode);
        iBarcodeParseRuleApi.enable(barcodeParseRule.getId());
        barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule(barcodeCode);
        Assertions.assertTrue(barcodeParseRule.isEnable());
    }

    @Test
    @Order(5)
    void testParse() {
        BarcodeParseRequestDTO barcodeParseRequestDTO = new BarcodeParseRequestDTO();
        barcodeParseRequestDTO.setBusinessFlow(BusinessFlowEnum.INBOUND);
        barcodeParseRequestDTO.setExecuteTime(ExecuteTimeEnum.SCAN_SKU);
        barcodeParseRequestDTO.setBarcode("123");
        List<BarcodeParseResult> parseResults = iBarcodeParseRuleApi.parse(barcodeParseRequestDTO);

        Assertions.assertEquals(1, parseResults.size());
        Assertions.assertEquals("123", parseResults.get(0).getFileValue());
    }
}
