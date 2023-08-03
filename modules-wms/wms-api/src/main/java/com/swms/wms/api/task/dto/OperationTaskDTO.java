package com.swms.wms.api.task.dto;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty
    private String warehouseCode;

    @NotEmpty
    private String taskNo;
    @NotNull
    private OperationTaskTypeEnum taskType;

    @NotNull
    private Long workStationId;

    @NotEmpty
    private String skuCode;
    private String skuName;
    private String skuUrl;

    @NotNull
    private Long skuBatchStockId;
    @NotNull
    private Long containerStockId;

    private Map<String, Object> batchAttributeJson;

    private String sourceContainerCode;
    private String sourceContainerSlot;

    private String boxNo;

    private boolean processed;

    @NotNull
    @Min(1)
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
