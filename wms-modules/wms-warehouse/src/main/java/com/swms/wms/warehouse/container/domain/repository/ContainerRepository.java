package com.swms.wms.warehouse.container.domain.repository;

import com.swms.wms.warehouse.container.domain.entity.Container;

public interface ContainerRepository {
    Container findByContainerCode(String containerCode);
}
