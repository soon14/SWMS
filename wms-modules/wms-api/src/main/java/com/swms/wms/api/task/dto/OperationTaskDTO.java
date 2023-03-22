package com.swms.wms.api.task.dto;

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

    private String stationCode;

    private Long taskId;
    private String skuCode;
    private String skuName;
    private String skuUrl;
    private Long skuBatchId;
    private Map<String, Object> batchAttributeJson;
//    private List<SnInfo> snInfos;

    private String sourceContainerCode;
    private String sourceContainerSlot;

    private boolean processed;

    private Integer requiredQty;
    private Integer operatedQty;
    private Integer toBeOperatedQty;

    private String targetLocationCode;
    private String targetContainerCode;
    private String targetContainerSlot;

    private OperationTaskTypeEnum taskType;
}
