package com.swms.mdm.config.infrastructure.persistence.mapper;

import com.swms.mdm.api.config.constants.ConfigApplyObjectEnum;
import com.swms.mdm.config.infrastructure.persistence.po.ParameterConfigPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParameterConfigPORepository extends JpaRepository<ParameterConfigPO, Long> {
    List<ParameterConfigPO> findByConfigApplyObject(ConfigApplyObjectEnum configApplyObject);

    ParameterConfigPO findByCode(String code);
}
