package com.swms.wms.warehouse.container.domain.entity;

import lombok.Data;

@Data
public class ContainerSlot {

    private Long id;

    private String containerSlotCode;
    private String containerSlotSpecCode;

    private String face;

    private Integer level;
    private Integer bay;

    private Long parentId;
    private String containerCode;
}
