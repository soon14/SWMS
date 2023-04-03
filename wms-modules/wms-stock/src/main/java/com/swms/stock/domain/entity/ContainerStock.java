package com.swms.stock.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContainerStock {

    private Long id;

    private Long skuBatchAttributeId;

    private String containerCode;
    private String containerSlotCode;

    private Integer totalQty;
    private Integer availableQty;
    // outbound locked qty
    private Integer outboundLockedQty;
    // other operation locked qty in the warehouse
    private Integer noOutboundLockedQty;
}
