package com.swms.stock.domain.entity;

import lombok.Data;

/**
 * stock design rule:
 * 1. stock created by receiving;
 * 2. stock transfer from one area to another area in warehouse;
 * 3. stock subtraction by shipping. if our system don't contain shipping module, then scheduled delete shipping area stock;
 */
@Data
public class SkuBatchStock {

    private Long id;

    private Long skuId;

    //unique key union skuBatchAttributeId and warehouseAreaCode
    private Long skuBatchAttributeId;
    private String warehouseAreaCode;

    private String warehouseCode;

    private Integer totalQty;
    private Integer availableQty;
    // outbound locked qty
    private Integer outboundLockedQty;
    // other operation locked qty in the warehouse
    private Integer noOutboundLockedQty;
}
