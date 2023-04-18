package com.swms.inbound.domain.entity;

import com.google.common.base.Preconditions;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
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

    private List<AcceptOrderDetail> acceptOrderDetails;

    public void accept(Integer acceptQty, Long acceptOrderDetailId, SortedMap<String, Object> batchAttributes) {
        Optional<AcceptOrderDetail> optional = this.getAcceptOrderDetails().stream()
            .filter(v -> v.getId().equals(acceptOrderDetailId))
            .findFirst();
        Preconditions.checkState(optional.isPresent(), "accept data is wrong");

        AcceptOrderDetail acceptOrderDetail = optional.get();
        acceptOrderDetail.accept(acceptQty, batchAttributes);
    }

    public void beginAccepting() {
        if (acceptOrderStatus == AcceptOrderStatusEnum.NEW) {
            acceptOrderStatus = AcceptOrderStatusEnum.ACCEPTING;
        }
    }

    public void completeAccepting() {
        acceptOrderDetails.forEach(AcceptOrderDetail::completeAccepting);
        acceptOrderStatus = AcceptOrderStatusEnum.ACCEPTED;
    }

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

        private Integer qtyReceived = 0;
        private Integer qtyAccepted = 0;
        private Integer qtyAbnormal = 0;

        private String rejectContainerCode;
        private String abnormalReason;

        private String skuCode;
        private String packageCode;
        private SortedMap<String, Object> batchAttributes = new TreeMap<>();
        private String skuName;
        private String ownerCode;
        private String ownerName;

        private String stationCode;

        private TreeMap<String, Object> extendFields = new TreeMap<>();

        public void accept(Integer acceptQty, SortedMap<String, Object> batchAttributes) {
            this.qtyAccepted += acceptQty;
            this.batchAttributes = batchAttributes;
            validateQty();
        }

        public void validateQty() {
            Preconditions.checkState(this.qtyAccepted + this.qtyAbnormal > this.qtyReceived, "over accept is not allowed");
        }

        public void completeAccepting() {
            this.qtyAbnormal = this.qtyReceived - this.qtyAccepted;
            validateQty();
        }
    }
}
