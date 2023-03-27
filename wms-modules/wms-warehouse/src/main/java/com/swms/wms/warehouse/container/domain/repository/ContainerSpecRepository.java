package com.swms.wms.warehouse.container.domain.repository;

import com.swms.wms.warehouse.container.domain.entity.ContainerSpec;

public interface ContainerSpecRepository {
    ContainerSpec getContainerSpecByCode(String containerSpecCode);
}
