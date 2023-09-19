package com.swms.outbound.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.TreeMap;

@Data
@Accessors(chain = true)
public class OutboundOrderPlanPreAllocatedRecord {

    private Long id;

    private Long outboundPlanOrderId;
    private Long outboundPlanOrderDetailId;

    private Long skuId;
    private TreeMap<String, Object> batchAttributes;

    private Long skuBatchStockId;

    private Integer qtyPreAllocated;

}
