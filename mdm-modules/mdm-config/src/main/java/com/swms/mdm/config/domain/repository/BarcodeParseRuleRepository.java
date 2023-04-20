package com.swms.mdm.config.domain.repository;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;

import java.util.List;

public interface BarcodeParseRuleRepository {
    void save(BarcodeParseRule toBarcodeParseRule);

    BarcodeParseRule getBarcodeParseRule(String barcodeRuleCode);

    List<BarcodeParseRule> findAllByOwnerCode(String ownerCode);

    List<BarcodeParseRule> findAll();

    BarcodeParseRule findById(Long barcodeParseRuleId);

    List<BarcodeParseRule> find(BusinessFlowEnum businessFlow, ExecuteTimeEnum executeTime);

    List<BarcodeParseRule> findByOwnerCodeOrderBrand(String ownerCode, String brand);
}
