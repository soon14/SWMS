package com.swms.outbound.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class OutboundPlanOrderDetail {

    private Long id;
    private Long outboundPlanOrderId;
    private Long skuId;
    private String skuCode;
    private String skuName;

    private Map<String, Object> batchAttributes;

    private Integer qtyRequired;
    private Integer qtyActual;

    private Map<String, String> reservedFields;
}
