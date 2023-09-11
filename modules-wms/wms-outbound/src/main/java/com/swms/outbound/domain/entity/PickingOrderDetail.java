package com.swms.outbound.domain.entity;

import java.util.TreeMap;

public class PickingOrderDetail {

    private Long skuId;
    private TreeMap<String, Object> batchAttributes;

    private Long skuBatchId;

    private Integer qtyRequired;
    private Integer qtyActual;

}
