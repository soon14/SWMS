package com.swms.mdm.config.infrastructure.impl;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;
import com.swms.mdm.config.domain.repository.BarcodeParseRuleRepository;
import com.swms.mdm.config.infrastructure.persistence.mapper.BarcodeParseRulePORepository;
import com.swms.mdm.config.infrastructure.persistence.transfer.BarcodeParseRulePOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarcodeParseRuleRepositoryImpl implements BarcodeParseRuleRepository {

    @Autowired
    private BarcodeParseRulePORepository barcodeParseRulePORepository;

    @Autowired
    private BarcodeParseRulePOTransfer barcodeParseRulePOTransfer;

    @Override
    public void save(BarcodeParseRule toBarcodeParseRule) {
        barcodeParseRulePORepository.save(barcodeParseRulePOTransfer.toPO(toBarcodeParseRule));
    }

    @Override
    public BarcodeParseRule getBarcodeParseRule(String barcodeRuleCode) {
        return barcodeParseRulePOTransfer.toDO(barcodeParseRulePORepository.findByCode(barcodeRuleCode));
    }

    @Override
    public BarcodeParseRule findById(Long barcodeParseRuleId) {
        return barcodeParseRulePOTransfer.toDO(barcodeParseRulePORepository.findById(barcodeParseRuleId).orElseThrow());
    }

    @Override
    public List<BarcodeParseRule> findByBusinessFlowAndExecuteTime(BusinessFlowEnum businessFlow, ExecuteTimeEnum executeTime) {
        return barcodeParseRulePOTransfer.toDOS(barcodeParseRulePORepository.findByBusinessFlowAndExecuteTime(businessFlow, executeTime));
    }
}
