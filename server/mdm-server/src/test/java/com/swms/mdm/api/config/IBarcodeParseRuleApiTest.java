package com.swms.mdm.api.config;

import com.swms.mdm.api.MdmTestApplication;
import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import com.swms.mdm.api.config.dto.BarcodeParseRequestDTO;
import com.swms.mdm.api.config.dto.BarcodeParseResult;
import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MdmTestApplication.class})
class IBarcodeParseRuleApiTest {

    @Autowired
    private IBarcodeParseRuleApi iBarcodeParseRuleApi;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testSave() {
        BarcodeParseRuleDTO barcodeParseRuleDTO = new BarcodeParseRuleDTO();
        barcodeParseRuleDTO.setBusinessFlow(BusinessFlowEnum.INBOUND);
        barcodeParseRuleDTO.setCode("123");
        barcodeParseRuleDTO.setEnable(true);
        barcodeParseRuleDTO.setExecuteTime(ExecuteTimeEnum.SCAN_SKU);
        barcodeParseRuleDTO.setRegularExpression("(.*)");
//        barcodeParseRuleDTO.setName("解析123");
        barcodeParseRuleDTO.setResultFields(Lists.newArrayList("skuCode"));
        Assertions.assertDoesNotThrow(() -> iBarcodeParseRuleApi.save(barcodeParseRuleDTO));
    }

    @Test
    void testEnable() {
        Assertions.assertDoesNotThrow(() -> iBarcodeParseRuleApi.enable(null));
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
