package com.swms.wms.basic.container.domain.repository;

import com.swms.wms.basic.container.domain.entity.Container;

public interface ContainerRepository {
    Container findByContainerCode(String containerCode);

    void save(Container container);
}
