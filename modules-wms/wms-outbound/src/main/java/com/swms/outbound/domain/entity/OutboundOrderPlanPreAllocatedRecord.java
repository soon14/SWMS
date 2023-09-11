package com.swms.outbound.domain.entity;

import java.util.TreeMap;

public class OutboundOrderPlanPreAllocatedRecord {

    private Long id;

    private Long outboundOrderId;
    private Long outboundOrderDetailId;

    private Long skuId;
    private TreeMap<String, Object> batchAttributes;

    private Long skuBatchId;

    private Integer qtyPreAllocated;

}
