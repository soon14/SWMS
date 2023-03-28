package com.swms.wms.warehouse.container.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContainerSlot {

    private Long id;

    private String containerSlotCode;
    private String containerSlotSpecCode;

    private String face;

    private Long parentId;
    private String containerCode;

    private BigDecimal occupationRatio;
}
