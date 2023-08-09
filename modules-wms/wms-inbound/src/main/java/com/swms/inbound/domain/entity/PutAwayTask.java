package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import lombok.Data;

import java.util.Map;

@Data
public class PutAwayTask {

    private Long id;

    private String taskNo;

    private String warehouseCode;

    private String ownerCode;

    private String putAwayTaskType;

    private boolean abnormal;

    private Long totalQty;
    private Integer totalBox;

    private Map<String, Object> extendFields;

    private AcceptOrderStatusEnum acceptOrderStatus;


}
