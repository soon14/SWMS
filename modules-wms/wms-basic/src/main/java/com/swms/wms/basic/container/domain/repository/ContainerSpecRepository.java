package com.swms.wms.basic.container.domain.repository;

import com.swms.wms.basic.container.domain.entity.ContainerSpec;
import com.swms.wms.basic.container.infrastructure.persistence.po.ContainerSpecPO;

public interface ContainerSpecRepository {

    ContainerSpec findByContainerSpecCode(String containerSpecCode);

    void save(ContainerSpec containerSpec);
}
