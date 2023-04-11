package com.swms.wms.basic.container.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContainerSlot {

    private Long id;

    private String containerSlotCode;
    private String containerSlotSpecCode;

    private Long parentId;
    private String containerCode;

    private BigDecimal occupationRatio;

    // every container slot has a unique location code.
    private String locationCode;
}
