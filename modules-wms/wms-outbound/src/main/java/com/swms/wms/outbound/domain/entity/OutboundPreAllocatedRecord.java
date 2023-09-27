package com.swms.wms.outbound.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class OutboundPreAllocatedRecord {

    private Long id;

    private Long outboundPlanOrderId;
    private Long outboundPlanOrderDetailId;

    private Long skuId;
    private Map<String, Object> batchAttributes;

    private Long skuBatchStockId;

    private Integer qtyPreAllocated;

}
