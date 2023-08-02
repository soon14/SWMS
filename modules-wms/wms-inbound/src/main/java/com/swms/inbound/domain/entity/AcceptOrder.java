package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.AcceptMethodEnum;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import com.swms.wms.api.inbound.constants.AcceptTypeEnum;
import lombok.Data;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Data
public class AcceptOrder {

    private Long id;

    private String orderNo;

    private Long inboundPlanOrderId;

    private String warehouseCode;
    private String ownerCode;

    private AcceptMethodEnum acceptMethod;
    private AcceptTypeEnum acceptType;

    private boolean putAway;

    private Long totalQty;
    private Integer totalBox;

    private String remark;

    private AcceptOrderStatusEnum acceptOrderStatus;

    private List<AcceptOrderDetail> acceptOrderDetails;

    @Data
    public static class AcceptOrderDetail {

        private Long id;

        private Long inboundPlanOrderDetailId;
        private Long receiveOrderDetailId;

        private String boxNo;

        // if sku is loose , then they will be packed into a box
        private String packBoxNo;

        private String targetContainerCode;
        private String targetContainerSpecCode;
        private String targetContainerSlotCode;

        private Integer qtyAccepted = 0;

        private String skuCode;
        private String skuName;
        private String style;
        private String color;
        private String size;
        private String brand;

        private SortedMap<String, Object> batchAttributes = new TreeMap<>();

        private Long stationId;
    }
}
