package com.swms.mdm.config.application;

import com.google.common.collect.Lists;
import com.swms.mdm.api.config.IBarcodeParseRuleApi;
import com.swms.mdm.api.config.dto.BarcodeParseRequestDTO;
import com.swms.mdm.api.config.dto.BarcodeParseResult;
import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.mdm.api.main.data.ISkuApi;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;
import com.swms.mdm.config.domain.repository.BarcodeParseRuleRepository;
import com.swms.mdm.config.domain.transfer.BarcodeParseRuleTransfer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarcodeParseRuleApplicationImpl implements IBarcodeParseRuleApi {

    @Autowired
    private BarcodeParseRuleRepository barcodeRuleRepository;

    @Autowired
    private BarcodeParseRuleTransfer barcodeParseRuleTransfer;

    @Autowired
    private ISkuApi iSkuApi;

    @Override
    public void save(BarcodeParseRuleDTO barcodeParseRuleDTO) {
        barcodeRuleRepository.save(barcodeParseRuleTransfer.toBarcodeParseRule(barcodeParseRuleDTO));
    }

    @Override
    public void update(BarcodeParseRuleDTO barcodeParseRuleDTO) {
        barcodeRuleRepository.save(barcodeParseRuleTransfer.toBarcodeParseRule(barcodeParseRuleDTO));
    }

    @Override
    public List<BarcodeParseResult> parse(BarcodeParseRequestDTO barcodeParseRequestDTO) {

        List<BarcodeParseRule> barcodeParseRules = queryParseRules(barcodeParseRequestDTO);
        if (CollectionUtils.isEmpty(barcodeParseRules)) {
            // null is ok, means no rule
            return null;
        }

        for (BarcodeParseRule parseRule : barcodeParseRules) {
            List<BarcodeParseResult> results = parseRule.parse(barcodeParseRequestDTO.getBarcode());
            if (CollectionUtils.isNotEmpty(results)) {
                return results;
            }
        }
        return Lists.newArrayList();
    }

    private List<BarcodeParseRule> queryParseRules(BarcodeParseRequestDTO barcodeParseRequestDTO) {

        //1. know sku
        if (CollectionUtils.isNotEmpty(barcodeParseRequestDTO.getKnownSkus())) {
            return barcodeParseRequestDTO.getKnownSkus().stream().flatMap(knownSku -> {
                SkuMainDataDTO skuMainDataDTO = iSkuApi.getSkuMainDataDTO(knownSku.getSkuCode(), knownSku.getOwnerCode());
                String barcodeRuleCode = skuMainDataDTO.getSkuConfig().getBarcodeRuleCode();
                if (StringUtils.isNotEmpty(barcodeRuleCode)) {
                    BarcodeParseRule barcodeParseRule = barcodeRuleRepository.getBarcodeParseRule(barcodeRuleCode);
                    if (barcodeParseRule.getExecuteTime() == barcodeParseRequestDTO.getExecuteTime()
                        && barcodeParseRule.getBusinessFlow() == barcodeParseRequestDTO.getBusinessFlow()) {
                        return Lists.newArrayList(barcodeParseRule).stream();
                    }
                }
                return barcodeRuleRepository.findAllByOwnerCode(knownSku.getOwnerCode())
                    .stream()
                    .filter(barcodeParseRule -> barcodeParseRule.getExecuteTime() == barcodeParseRequestDTO.getExecuteTime()
                        && barcodeParseRule.getBusinessFlow() == barcodeParseRequestDTO.getBusinessFlow()
                        && StringUtils.equals(barcodeParseRule.getBrand(), knownSku.getBrand()))
                    ;
            }).toList();
        }

        //2. don't know sku,but know owner
        if (StringUtils.isNotEmpty(barcodeParseRequestDTO.getOwnerCode())) {
            return barcodeRuleRepository.findAllByOwnerCode(barcodeParseRequestDTO.getOwnerCode())
                .stream()
                .filter(barcodeParseRule -> barcodeParseRule.getExecuteTime() == barcodeParseRequestDTO.getExecuteTime()
                    && barcodeParseRule.getBusinessFlow() == barcodeParseRequestDTO.getBusinessFlow())
                .toList();
        }

        //3. don't know sku and owner
        return barcodeRuleRepository.findAll().stream()
            .filter(barcodeParseRule -> barcodeParseRule.getExecuteTime() == barcodeParseRequestDTO.getExecuteTime()
                && barcodeParseRule.getBusinessFlow() == barcodeParseRequestDTO.getBusinessFlow())
            .toList();
    }

    @Override
    public void enable(Long barcodeParseRuleId) {
        BarcodeParseRule barcodeParseRule = barcodeRuleRepository.findById(barcodeParseRuleId);
        barcodeParseRule.enable();
        barcodeRuleRepository.save(barcodeParseRule);
    }

    @Override
    public void disable(Long barcodeParseRuleId) {
        BarcodeParseRule barcodeParseRule = barcodeRuleRepository.findById(barcodeParseRuleId);
        barcodeParseRule.disable();
        barcodeRuleRepository.save(barcodeParseRule);
    }
}
