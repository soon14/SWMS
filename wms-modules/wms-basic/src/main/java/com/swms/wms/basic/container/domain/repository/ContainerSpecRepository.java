package com.swms.wms.basic.container.domain.repository;

import com.swms.wms.basic.container.domain.entity.ContainerSpec;

public interface ContainerSpecRepository {
    ContainerSpec findByContainerSpecCode(String containerSpecCode);
}
