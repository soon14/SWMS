package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.ReceiveOrderStatusEnum;
import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class ReceiveOrder {

    private Long id;

    private String orderNo;

    private String warehouseCode;
    private String warehouseName;

    /**
     * redundancy fields , according to ReceiveOrderDetail ownerCode and ownerName
     */
    private String ownerCode;
    private String ownerName;

    private String receiveOrderType;

    private boolean abnormal;

    private Long totalQty;
    private Integer totalBox;

    private Map<String, Object> extendFields;

    private ReceiveOrderStatusEnum receiveOrderStatus;

    @Data
    public static class ReceiveOrderDetail {

        private Long id;

        private Long inboundPlanOrderDetailId;
        private Long receiveOrderId;

        private String containerCode;
        private String containerSpecCode;
        private String containerSlotCode;

        private String boxNo;

        private Integer qtyRestocked = 0;
        private Integer qtyReceived = 0;
        private Integer qtyAccepted = 0;
        private Integer qtyAbnormal = 0;

        private String rejectContainerCode;
        private String abnormalReason;

        private String skuCode;
        private String packageCode;
        private Map<String, Object> batchAttributes = new TreeMap<>();
        private String skuName;
        private String ownerCode;
        private String ownerName;

        private Map<String, Object> extendFields = new TreeMap<>();
    }
}
