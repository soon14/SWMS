package com.swms.stock.domain.entity;

import lombok.Data;

@Data
public class ContainerStock {

    private Long id;

    private Long skuId;
    private Long skuBatchId;

    private Long containerId;
    private Long containerSlotId;

    private String containerCode;
    private String containerSlot;

    private Integer totalQty;
    private Integer availableQty;
    // outbound locked qty
    private Integer outboundLockedQty;
    // other operation locked qty in the warehouse
    private Integer noOutboundLockedQty;
}
