package com.swms.mdm.api.config;

import com.swms.mdm.api.config.dto.BarcodeParseRequestDTO;
import com.swms.mdm.api.config.dto.BarcodeParseResult;
import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;

import java.util.List;

public interface IBarcodeParseRuleApi {

    void save(BarcodeParseRuleDTO barcodeParseRuleDTO);

    void update(BarcodeParseRuleDTO barcodeParseRuleDTO);

    List<BarcodeParseResult> parse(BarcodeParseRequestDTO barcodeParseRequestDTO);

    void enable(Long barcodeParseRuleId);

    void disable(Long barcodeParseRuleId);
}
