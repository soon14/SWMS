package com.swms.mdm.config.domain.repository;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;

import java.util.List;

public interface BarcodeParseRuleRepository {

    void save(BarcodeParseRule toBarcodeParseRule);

    BarcodeParseRule getBarcodeParseRule(String barcodeRuleCode);

    BarcodeParseRule findById(Long barcodeParseRuleId);

    List<BarcodeParseRule> findByBusinessFlowAndExecuteTime(BusinessFlowEnum businessFlow, ExecuteTimeEnum executeTime);

}
