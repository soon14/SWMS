package com.swms.outbound.domain.entity;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class OutboundPlanOrderDetail {

    private String skuCode;
    private String skuName;

    private TreeMap<String, Object> batchAttributes;

    private Integer qtyRequired;
    private Integer qtyActual;

    private Map<String, String> reservedFields;
}
