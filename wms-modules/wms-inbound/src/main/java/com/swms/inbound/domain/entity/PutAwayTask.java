package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import lombok.Data;

import java.util.TreeMap;

@Data
public class PutAwayTask {

    private Long id;

    private String taskNo;

    private String warehouseCode;
    private String warehouseName;

    /**
     * redundancy fields , according to PutAwayTask ownerCode and ownerName
     */
    private String ownerCode;
    private String ownerName;

    private String putAwayTaskType;

    private boolean abnormal;

    private Long totalQty;
    private Integer totalBox;

    private TreeMap<String, Object> extendFields;

    private AcceptOrderStatusEnum acceptOrderStatus;
}
