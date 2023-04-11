package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;
import lombok.Data;

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

    private InboundPlanOrderStatusEnum inboundOrderStatus = InboundPlanOrderStatusEnum.NEW;

    private Long estimatedArrivalTime;

    private Integer skuKindNum;
    private Long totalQty;
    private Integer totalBox;

    private TreeMap<String, Object> extendFields;
}
