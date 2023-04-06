package com.swms.wms.basic.container.domain.repository;

import com.swms.wms.basic.container.domain.entity.ContainerSlot;

import java.util.List;

public interface ContainerSlotRepository {
    List<ContainerSlot> findAllByContainerCodeAndFace(String containerCode, String face);
}
