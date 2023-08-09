package com.swms.mdm.config.infrastructure.persistence.mapper;

import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.config.infrastructure.persistence.po.ParameterConfigPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterConfigPORepository extends JpaRepository<ParameterConfigPO, Long> {

    ParameterConfigPO findByCode(ParameterCodeEnum code);
}
