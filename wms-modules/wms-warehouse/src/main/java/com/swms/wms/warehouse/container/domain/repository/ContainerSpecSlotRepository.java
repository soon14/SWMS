package com.swms.wms.warehouse.container.domain.repository;

import com.swms.wms.warehouse.container.domain.entity.ContainerSlotSpec;

import java.util.List;

public interface ContainerSpecSlotRepository {
    List<ContainerSlotSpec> findAllByContainerSpecCode(String containerSpecCode);

    List<ContainerSlotSpec> findAllByContainerSpecCodeAndFace(String containerSpecCode, String face);
}
