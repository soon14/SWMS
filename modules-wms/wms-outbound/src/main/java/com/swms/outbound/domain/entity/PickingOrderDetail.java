package com.swms.outbound.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.TreeMap;

@Data
@Accessors(chain = true)
public class PickingOrderDetail {

    private Long outboundOrderPlanId;
    private Long outboundOrderPlanDetailId;
    private Long skuId;
    private TreeMap<String, Object> batchAttributes;

    private Long skuBatchId;

    private Integer qtyRequired;
    private Integer qtyActual;

}
