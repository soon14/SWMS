package com.swms.inbound.domain.entity;

import static com.swms.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_STATUS_ERROR;

import com.google.common.base.Preconditions;
import com.swms.utils.exception.WmsException;
import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;
import lombok.Data;

import java.util.List;
import java.util.TreeMap;

@Data
public class InboundPlanOrder {

    private Long id;

    private String orderNo;
    private String customerOrderNo;
    private String lpnCode;

    private String warehouseCode;
    private String warehouseName;

    /**
     * redundancy fields , according to InboundPlanOrderDetail ownerCode and ownerName
     */
    private String ownerCode;
    private String ownerName;

    private String inboundOrderType;

    private boolean abnormal;

    private InboundPlanOrderStatusEnum inboundPlanOrderStatus = InboundPlanOrderStatusEnum.NEW;

    private Long estimatedArrivalTime;

    private Integer skuKindNum;
    private Long totalQty;
    private Integer totalBox;

    private TreeMap<String, Object> extendFields;

    private List<InboundPlanOrderDetail> inboundPlanOrderDetails;

    private Long version;

    @Data
    public static class InboundPlanOrderDetail {

        private Long id;

        private Long inboundPlanOrderId;

        private String containerCode;
        private String containerSpecCode;
        private String containerSlotCode;

        private String boxNo;

        private Integer qtyRestocked = 0;
        private Integer qtyReceived = 0;
        private Integer qtyAccepted = 0;
        private Integer qtyPutAway = 0;
        private Integer qtyAbnormal = 0;

        private String abnormalReason;

        private String skuCode;
        private String packageCode;
        private TreeMap<String, Object> batchAttributes = new TreeMap<>();
        private String skuName;
        private String ownerCode;
        private String ownerName;

        private TreeMap<String, Object> extendFields = new TreeMap<>();
    }

    public void cancel() {
        if (inboundPlanOrderStatus != InboundPlanOrderStatusEnum.NEW) {
            throw new WmsException(INBOUND_STATUS_ERROR);
        }
        this.inboundPlanOrderStatus = InboundPlanOrderStatusEnum.CANCEL;
    }

    public void beginReceiving() {
        if (inboundPlanOrderStatus != InboundPlanOrderStatusEnum.NEW) {
            throw new WmsException(INBOUND_STATUS_ERROR);
        }
        this.inboundPlanOrderStatus = InboundPlanOrderStatusEnum.RECEIVING;
    }
}
