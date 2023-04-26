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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class BarcodeParseRuleApiTest extends BaseTest {

    @Autowired
    private IBarcodeParseRuleApi iBarcodeParseRuleApi;

    @Autowired
    private BarcodeParseRuleRepository barcodeParseRuleRepository;

    @Test
    void testSave() {
        BarcodeParseRuleDTO barcodeParseRuleDTO = new BarcodeParseRuleDTO();
        barcodeParseRuleDTO.setBusinessFlow(BusinessFlowEnum.INBOUND);
        barcodeParseRuleDTO.setCode("1234");
        barcodeParseRuleDTO.setEnable(true);
        barcodeParseRuleDTO.setExecuteTime(ExecuteTimeEnum.SCAN_SKU);
        barcodeParseRuleDTO.setRegularExpression("(.*)");
        barcodeParseRuleDTO.setName("解析123");
        barcodeParseRuleDTO.setResultFields(Lists.newArrayList("skuCode"));
        Assertions.assertDoesNotThrow(() -> iBarcodeParseRuleApi.save(barcodeParseRuleDTO));
    }

    @Test
    void testUpdate() {
//        BarcodeParseRule barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule("123");
//        BarcodeParseRuleDTO barcodeParseRuleDTO = new BarcodeParseRuleDTO();
//        barcodeParseRuleDTO.setId(438390613814153216L);
//        barcodeParseRuleDTO.setBusinessFlow(BusinessFlowEnum.INBOUND);
//        barcodeParseRuleDTO.setCode("123");
//        barcodeParseRuleDTO.setEnable(true);
//        barcodeParseRuleDTO.setExecuteTime(ExecuteTimeEnum.SCAN_SKU);
//        barcodeParseRuleDTO.setRegularExpression("(.*)");
//        barcodeParseRuleDTO.setName("解析1234");
//        barcodeParseRuleDTO.setResultFields(Lists.newArrayList("skuCode"));
//        barcodeParseRuleDTO.setVersion(barcodeParseRule.getVersion());
//        Assertions.assertDoesNotThrow(() -> iBarcodeParseRuleApi.update(barcodeParseRuleDTO));
    }

    @Test
    void testEnable() {
        BarcodeParseRule barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule("123");
        iBarcodeParseRuleApi.enable(barcodeParseRule.getId());
        barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule("123");
        Assertions.assertTrue(barcodeParseRule.isEnable());
    }

    @Test
    void testDisable() {
        BarcodeParseRule barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule("123");
        iBarcodeParseRuleApi.disable(barcodeParseRule.getId());
        barcodeParseRule = barcodeParseRuleRepository.getBarcodeParseRule("123");
        Assertions.assertFalse(barcodeParseRule.isEnable());
    }

    @Test
    void testParse() {
        BarcodeParseRequestDTO barcodeParseRequestDTO = new BarcodeParseRequestDTO();
        barcodeParseRequestDTO.setBusinessFlow(BusinessFlowEnum.INBOUND);
        barcodeParseRequestDTO.setExecuteTime(ExecuteTimeEnum.SCAN_SKU);
        barcodeParseRequestDTO.setBarcode("123");
        List<BarcodeParseResult> parseResults = iBarcodeParseRuleApi.parse(barcodeParseRequestDTO);

        Assertions.assertEquals(1, parseResults.size());
        Assertions.assertEquals(parseResults.get(0).getFileValue(), "123");
    }
}
