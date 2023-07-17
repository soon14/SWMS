package com.swms.wms.basic.container.domain.repository;

import com.swms.wms.basic.container.domain.entity.Container;

import java.util.List;

public interface ContainerRepository {

    Container findByContainerCode(String containerCode, String warehouseCode);

    void save(Container container);

    void saveAll(List<Container> containers);

}
