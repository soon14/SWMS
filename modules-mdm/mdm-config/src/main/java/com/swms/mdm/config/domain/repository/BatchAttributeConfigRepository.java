package com.swms.mdm.config.domain.repository;

import com.swms.mdm.config.domain.entity.BatchAttributeConfig;

import java.util.List;

public interface BatchAttributeConfigRepository {

    List<BatchAttributeConfig> findAll();

    void save(BatchAttributeConfig toBatchAttributeConfig);

    BatchAttributeConfig findById(Long id);

    BatchAttributeConfig findByCode(String code);
}
