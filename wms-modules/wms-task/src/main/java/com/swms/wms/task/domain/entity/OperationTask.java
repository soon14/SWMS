package com.swms.wms.task.domain.entity;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.Data;

@Data
public class OperationTask {

    private Long id;

    private String taskNo;
    private OperationTaskTypeEnum taskType;

    private String stationCode;

    private String skuCode;
    private Long skuBatchId;
    private Long stockId;

    private String sourceContainerCode;
    private String sourceContainerSlot;

    private Integer requiredQty;
    private Integer operatedQty;
    private Integer abnormalQty;

    private String targetLocationCode;
    private String targetContainerCode;
    private String targetContainerSlot;

    private Long originalOrderId;
    private Long originalOrderDetailId;

    private OperationTaskStatusEnum taskStatus;
}
