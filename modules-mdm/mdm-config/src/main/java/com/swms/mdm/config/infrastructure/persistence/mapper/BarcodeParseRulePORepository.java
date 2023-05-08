package com.swms.mdm.config.infrastructure.persistence.mapper;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import com.swms.mdm.config.infrastructure.persistence.po.BarcodeParseRulePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarcodeParseRulePORepository extends JpaRepository<BarcodeParseRulePO, Long> {
    BarcodeParseRulePO findByCode(String barcodeRuleCode);

    List<BarcodeParseRulePO> findByBusinessFlowAndExecuteTime(BusinessFlowEnum businessFlow, ExecuteTimeEnum executeTime);
}
