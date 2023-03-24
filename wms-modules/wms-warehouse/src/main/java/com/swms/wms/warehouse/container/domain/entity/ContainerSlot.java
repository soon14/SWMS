package com.swms.wms.warehouse.container.domain.entity;

import lombok.Data;

@Data
public class ContainerSlot {

    private Long id;

    private String containerSlotCode;
    private String containerSlotSpecCode;

    private Integer level;
    private Integer bay;

    private Long parentContainerSlotId;
}
