package com.swms.outbound.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class PickingOrderDetail {

    private Long id;
    private Long pickingOrderId;
    private Long outboundOrderPlanId;
    private Long outboundOrderPlanDetailId;
    private Long skuId;
    private Map<String, Object> batchAttributes;

    private Long skuBatchStockId;

    private Integer qtyRequired;
    private Integer qtyActual;

}
