package com.swms.stock.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContainerStock {

    private Long id;

    private Long skuBatchAttributeId;

    /**
     * container is not must be a physical container. e.g.
     * when sku received to a place but not a physical container,
     * then the container code will be received order no ,
     * and when sku put away on the rack, then the container code is the location code;
     */
    private String containerCode;
    private String containerSlotCode;

    private Integer totalQty;
    private Integer availableQty;
    // outbound locked qty
    private Integer outboundLockedQty;
    // other operation locked qty in the warehouse
    private Integer noOutboundLockedQty;

    /**
     * it means the container is or not a physical container
     */
    private boolean boxStock;
}
