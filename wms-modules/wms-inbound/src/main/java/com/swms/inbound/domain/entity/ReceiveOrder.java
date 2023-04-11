package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.ReceiveOrderStatusEnum;
import lombok.Data;

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

    private TreeMap<String, Object> extendFields;

    private ReceiveOrderStatusEnum receiveOrderStatus;
}
