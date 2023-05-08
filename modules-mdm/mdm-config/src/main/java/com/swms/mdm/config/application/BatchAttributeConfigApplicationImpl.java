package com.swms.mdm.config.application;

import static com.swms.utils.constants.RedisConstants.BARCODE_PARSE_RULE_ADD_LOCK;
import static com.swms.utils.constants.RedisConstants.BATCH_ATTRIBUTE_CONFIG_ADD_LOCK;
import static com.swms.utils.exception.code_enum.CommonErrorDescEnum.REPEAT_REQUEST;
import static com.swms.utils.exception.code_enum.MainDataErrorDescEnum.BARCODE_PARSE_RULE_REPEAT;

import com.swms.mdm.api.config.IBatchAttributeConfigApi;
import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.mdm.config.domain.entity.BatchAttributeConfig;
import com.swms.mdm.config.domain.repository.BatchAttributeConfigRepository;
import com.swms.mdm.config.domain.transfer.BatchAttributeConfigTransfer;
import com.swms.utils.exception.WmsException;
import com.swms.utils.lock.DistributeLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class BatchAttributeConfigApplicationImpl implements IBatchAttributeConfigApi {

    @Autowired
    private DistributeLock distributeLock;

    @Autowired
    private BatchAttributeConfigRepository batchAttributeConfigRepository;

    @Autowired
    private BatchAttributeConfigTransfer batchAttributeConfigTransfer;

    @Override
    public void save(BatchAttributeConfigDTO batchAttributeConfigDTO) {
        boolean lock = distributeLock.acquireLock(BATCH_ATTRIBUTE_CONFIG_ADD_LOCK, 1000);
        if (!lock) {
            throw new WmsException(REPEAT_REQUEST);
        }
        List<BatchAttributeConfig> batchAttributeConfigs = batchAttributeConfigRepository
            .findAll().stream().filter(BatchAttributeConfig::isEnable).toList();
        try {
            if (batchAttributeConfigs.stream().anyMatch(batchAttributeConfig ->
                batchAttributeConfig.match(batchAttributeConfigDTO.getOwnerCode(), batchAttributeConfigDTO.getSkuFirstCategory()))) {
                throw new WmsException(BARCODE_PARSE_RULE_REPEAT);
            }
            batchAttributeConfigRepository.save(batchAttributeConfigTransfer.toBatchAttributeConfig(batchAttributeConfigDTO));
        } finally {
            distributeLock.releaseLock(BATCH_ATTRIBUTE_CONFIG_ADD_LOCK);
        }
    }

    @Override
    public void update(BatchAttributeConfigDTO batchAttributeConfigDTO) {
        batchAttributeConfigRepository.save(batchAttributeConfigTransfer.toBatchAttributeConfig(batchAttributeConfigDTO));
    }

    @Override
    public BatchAttributeConfigDTO getByOwnerAndSkuFirstCategory(String ownerCode, String skuFirstCategory) {
        List<BatchAttributeConfig> batchAttributeConfigs = batchAttributeConfigRepository
            .findAll().stream().filter(BatchAttributeConfig::isEnable).toList();
        return batchAttributeConfigs.stream()
            .filter(batchAttributeConfig -> batchAttributeConfig.match(ownerCode, skuFirstCategory))
            .findFirst()
            .map(batchAttributeConfig -> batchAttributeConfigTransfer.toBatchAttributeConfigDTO(batchAttributeConfig))
            .orElse(null);
    }

}
