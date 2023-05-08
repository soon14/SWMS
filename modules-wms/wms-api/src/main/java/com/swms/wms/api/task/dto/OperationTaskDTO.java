package com.swms.wms.api.task.dto;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * abstract of operation task contains all tasks. eg: inbound, outbound, relocation, etc.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationTaskDTO {

    private Long id;

    private String taskNo;
    private OperationTaskTypeEnum taskType;

    private String stationCode;

    private String skuCode;
    private String skuName;
    private String skuUrl;

    private Long skuBatchAttributeId;
    private Long skuBatchStockId;
    private Long containerStockId;

    private Map<String, Object> batchAttributeJson;

    private String sourceContainerCode;
    private String sourceContainerSlot;

    private String boxNo;

    private boolean processed;

    private Integer requiredQty;
    private Integer operatedQty;
    private Integer toBeOperatedQty;

    private String targetLocationCode;
    private String targetContainerCode;
    private String targetContainerSlot;

    private Long originalOrderId;
    private Long originalOrderDetailId;

    private OperationTaskStatusEnum taskStatus;
}
