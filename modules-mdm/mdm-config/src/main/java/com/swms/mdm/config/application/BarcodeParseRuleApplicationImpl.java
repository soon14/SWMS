package com.swms.mdm.config.application;

import static com.swms.common.utils.constants.RedisConstants.BARCODE_PARSE_RULE_ADD_LOCK;
import static com.swms.common.utils.exception.code_enum.CommonErrorDescEnum.REPEAT_REQUEST;
import static com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum.BARCODE_PARSE_RULE_REPEAT;
import static com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum.CODE_MUST_NOT_UPDATE;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.swms.common.utils.exception.WmsException;
import com.swms.distribute.lock.DistributeLock;
import com.swms.mdm.api.config.IBarcodeParseRuleApi;
import com.swms.mdm.api.config.dto.BarcodeParseRequestDTO;
import com.swms.mdm.api.config.dto.BarcodeParseResult;
import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;
import com.swms.mdm.config.domain.repository.BarcodeParseRuleRepository;
import com.swms.mdm.config.domain.transfer.BarcodeParseRuleTransfer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Validated
@Service
public class BarcodeParseRuleApplicationImpl implements IBarcodeParseRuleApi {

    @Autowired
    private BarcodeParseRuleRepository barcodeRuleRepository;

    @Autowired
    private BarcodeParseRuleTransfer barcodeParseRuleTransfer;

    @Autowired
    private ISkuMainDataApi iSkuApi;

    @Autowired
    private DistributeLock distributeLock;

    @Override
    public void save(BarcodeParseRuleDTO barcodeParseRuleDTO) {
        distributeLock.acquireLockIfThrows(BARCODE_PARSE_RULE_ADD_LOCK, 1000);
        try {
            List<BarcodeParseRule> barcodeParseRules = barcodeRuleRepository
                .findByBusinessFlowAndExecuteTime(barcodeParseRuleDTO.getBusinessFlow(), barcodeParseRuleDTO.getExecuteTime());
            if (barcodeParseRules.stream().anyMatch(barcodeParseRule ->
                barcodeParseRule.match(barcodeParseRuleDTO.getOwnerCode(), barcodeParseRuleDTO.getBrand()))) {
                throw new WmsException(BARCODE_PARSE_RULE_REPEAT);
            }

            barcodeRuleRepository.save(barcodeParseRuleTransfer.toBarcodeParseRule(barcodeParseRuleDTO));
        } finally {
            distributeLock.releaseLock(BARCODE_PARSE_RULE_ADD_LOCK);
        }

    }

    @Override
    public void update(BarcodeParseRuleDTO barcodeParseRuleDTO) {
        BarcodeParseRule barcodeParseRule = barcodeRuleRepository.findById(barcodeParseRuleDTO.getId());
        if (!StringUtils.equals(barcodeParseRule.getCode(), barcodeParseRuleDTO.getCode())) {
            throw new WmsException(CODE_MUST_NOT_UPDATE);
        }
        Preconditions.checkState(Objects.equals(barcodeParseRule.getVersion(), barcodeParseRuleDTO.getVersion()));

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

        List<BarcodeParseRule> barcodeParseRules = barcodeRuleRepository
            .findByBusinessFlowAndExecuteTime(barcodeParseRequestDTO.getBusinessFlow(), barcodeParseRequestDTO.getExecuteTime())
            .stream().filter(BarcodeParseRule::isEnable).toList();

        //1. know sku
        if (CollectionUtils.isNotEmpty(barcodeParseRequestDTO.getKnownSkus())) {
            return barcodeParseRequestDTO.getKnownSkus().stream().flatMap(knownSku -> {
                SkuMainDataDTO skuMainDataDTO = iSkuApi.getSkuMainData(knownSku.getSkuCode(), knownSku.getOwnerCode());
                String barcodeRuleCode = skuMainDataDTO.getSkuConfig().getBarcodeRuleCode();
                if (StringUtils.isNotEmpty(barcodeRuleCode)) {
                    Optional<BarcodeParseRule> optional = barcodeParseRules.stream().filter(v -> StringUtils.equals(v.getCode(), barcodeRuleCode))
                        .findFirst();
                    if (optional.isPresent()) {
                        return Lists.newArrayList(optional.get()).stream();
                    }
                }
                return barcodeParseRules.stream()
                    .filter(barcodeParseRule -> barcodeParseRule.match(knownSku.getOwnerCode(), knownSku.getBrand()));
            }).toList();
        }

        //2. don't know sku,but know owner
        if (StringUtils.isNotEmpty(barcodeParseRequestDTO.getOwnerCode())) {
            return barcodeParseRules.stream()
                .filter(barcodeParseRule -> barcodeParseRule.matchOwner(barcodeParseRequestDTO.getOwnerCode()))
                .toList();
        }

        //3. don't know sku and owner
        return barcodeParseRules;
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
