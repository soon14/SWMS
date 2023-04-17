package com.swms.wms.task.infrastructure.persistence.po;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.Data;

@Data
public class OperationTaskPO {

    private Long id;

    private String taskNo;
    private OperationTaskTypeEnum taskType;

    private String stationCode;

    private String skuCode;
    private Long skuBatchAttributeId;
    private Long skuBatchStockId;
    private Long containerStockId;

    private String sourceContainerCode;
    private String sourceContainerSlot;

    private String boxNo;

    private Integer requiredQty;
    private Integer operatedQty;
    private Integer abnormalQty;

    private String targetLocationCode;
    private String targetContainerCode;
    private String targetContainerSlotCode;

    private Long originalOrderId;
    private Long originalOrderDetailId;

    private OperationTaskStatusEnum taskStatus;
}
