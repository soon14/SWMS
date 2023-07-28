package com.swms.mdm.config.infrastructure.repository.impl;

import com.swms.mdm.config.domain.entity.BatchAttributeConfig;
import com.swms.mdm.config.domain.repository.BatchAttributeConfigRepository;
import com.swms.mdm.config.infrastructure.persistence.mapper.BatchAttributeConfigPORepository;
import com.swms.mdm.config.infrastructure.persistence.transfer.BatchAttributeConfigPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchAttributeConfigRepositoryImpl implements BatchAttributeConfigRepository {

    @Autowired
    private BatchAttributeConfigPORepository batchAttributeConfigPORepository;

    @Autowired
    private BatchAttributeConfigPOTransfer batchAttributeConfigPOTransfer;

    @Override
    public List<BatchAttributeConfig> findAll() {
        return batchAttributeConfigPOTransfer.toDOS(batchAttributeConfigPORepository.findAll());
    }

    @Override
    public void save(BatchAttributeConfig toBatchAttributeConfig) {
        batchAttributeConfigPORepository.save(batchAttributeConfigPOTransfer.toPO(toBatchAttributeConfig));
    }

    @Override
    public BatchAttributeConfig findById(Long id) {
        return batchAttributeConfigPOTransfer.toDO(batchAttributeConfigPORepository.findById(id).orElseThrow());
    }
}
