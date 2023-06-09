package com.swms.mdm.config.infrastructure.persistence.mapper;

import com.swms.mdm.config.infrastructure.persistence.po.DictionaryPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryPORepository extends JpaRepository<DictionaryPO, Long> {

    DictionaryPO findByCode(String code);
}

