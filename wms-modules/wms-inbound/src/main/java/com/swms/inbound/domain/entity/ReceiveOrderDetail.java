package com.swms.inbound.domain.entity;

import lombok.Data;

import java.util.TreeMap;

@Data
public class ReceiveOrderDetail {

    private Long id;

    private Long inboundPlanOrderDetailId;
    private Long receiveOrderId;

    private String containerCode;
    private String containerSpecCode;
    private String containerSlotCode;

    private String boxNo;

    private Integer qtyReceived = 0;
    private Integer qtyAccepted = 0;
    private Integer qtyPutAway = 0;
    private Integer qtyAbnormal = 0;

    private String rejectContainerCode;
    private String abnormalReason;

    private String skuCode;
    private String packageCode;
    private TreeMap<String, Object> batchAttributes = new TreeMap<>();
    private String skuName;
    private String ownerCode;
    private String ownerName;

    private TreeMap<String, Object> extendFields = new TreeMap<>();
}
