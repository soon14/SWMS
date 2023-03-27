package com.swms.wms.warehouse.container.domain.repository;

import com.swms.wms.warehouse.container.domain.entity.ContainerSlot;

import java.util.List;

public interface ContainerSlotRepository {
    List<ContainerSlot> findAllByContainerCodeAndFace(String containerCode, String face);
}
