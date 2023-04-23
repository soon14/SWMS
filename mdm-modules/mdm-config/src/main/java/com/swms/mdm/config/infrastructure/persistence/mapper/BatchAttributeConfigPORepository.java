package com.swms.mdm.config.infrastructure.persistence.mapper;

import com.swms.mdm.config.infrastructure.persistence.po.BatchAttributeConfigPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchAttributeConfigPORepository extends JpaRepository<BatchAttributeConfigPO, Long> {
    BatchAttributeConfigPO findByCode(String batchAttributeConfigCode);
}
