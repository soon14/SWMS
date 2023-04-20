package com.swms.mdm.config.domain.repository;

import com.google.common.collect.Collections2;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;

import java.util.List;

public interface BarcodeParseRuleRepository {
    void save(BarcodeParseRule toBarcodeParseRule);

    BarcodeParseRule getBarcodeParseRule(String barcodeRuleCode);

    List<BarcodeParseRule> findAllByOwnerCode(String ownerCode);

    List<BarcodeParseRule> findAll();

    BarcodeParseRule findById(Long barcodeParseRuleId);
}
