package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import lombok.Data;

import java.util.TreeMap;

@Data
public class AcceptOrder {

    private Long id;

    private String orderNo;

    private String warehouseCode;
    private String warehouseName;

    /**
     * redundancy fields , according to ReceiveOrderDetail ownerCode and ownerName
     */
    private String ownerCode;
    private String ownerName;

    private String acceptOrderType;

    private boolean abnormal;

    private Long totalQty;
    private Integer totalBox;

    private TreeMap<String, Object> extendFields;

    private AcceptOrderStatusEnum acceptOrderStatus;

    @Data
    public static class AcceptOrderDetail {

        private Long id;

        private Long inboundPlanOrderDetailId;
        private Long receiveOrderDetailId;

        private String containerCode;
        private String containerSpecCode;
        private String containerSlotCode;

        private String boxNo;

        private String targetContainerCode;
        private String targetContainerSpecCode;
        private String targetContainerSlotCode;

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

        private String stationCode;

        private TreeMap<String, Object> extendFields = new TreeMap<>();
    }
}
