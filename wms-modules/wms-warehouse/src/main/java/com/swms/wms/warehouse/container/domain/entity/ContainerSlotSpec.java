package com.swms.wms.warehouse.container.domain.entity;

import lombok.Data;

@Data
public class ContainerSlotSpec {

    private String containerSpecCode;

    // unique identifier
    private String containerSlotSpecCode;

    private Integer length;
    private Integer width;
    private Integer height;
    private Long volume;

    private Integer level;
    private Integer bay;

    private Long parentContainerSlotSpecId;
}
