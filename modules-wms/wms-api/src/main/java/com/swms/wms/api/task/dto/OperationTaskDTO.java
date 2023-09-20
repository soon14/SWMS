package com.swms.wms.api.task.dto;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.stock.dto.SkuBatchAttributeDTO;
import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * abstract of operation task contains all tasks. eg: inbound, outbound, relocation, etc.
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class OperationTaskDTO {

    private Long id;

    @NotEmpty
    private String warehouseCode;

    @NotNull
    private OperationTaskTypeEnum taskType;

    private Long workStationId;

    @NotNull
    private Long skuId;

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

    private Long orderId;
    private Long detailId;

    private OperationTaskStatusEnum taskStatus;

    private SkuMainDataDTO skuMainDataDTO;

    private SkuBatchAttributeDTO skuBatchAttributeDTO;

    private Map<Long, String> assignedStationSlot;

    public OperationTaskDTO() {
        this.taskStatus = OperationTaskStatusEnum.CREATED;
    }
}
