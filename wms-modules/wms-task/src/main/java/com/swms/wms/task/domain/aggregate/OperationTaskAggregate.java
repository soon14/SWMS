package com.swms.wms.task.domain.aggregate;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.Data;

@Data
public class OperationTaskAggregate {

    private String taskNo;
    private OperationTaskTypeEnum taskType;

    private String skuCode;
    private Long skuBatchId;

    private Long stockId;

    private String sourceContainerCode;
    private String sourceContainerSlot;

    private Integer requiredQty;
    private Integer operatedQty;

    private String targetLocationCode;
    private String targetContainerCode;
    private String targetContainerSlot;

    private Long originalOrderId;
    private Long originalOrderDetailId;
}
