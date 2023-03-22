package com.swms.wms.api.task.dto;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.Data;

@Data
public class SplitTaskDTO {

    private Long taskId;
    private Integer requiredQty;
    private Integer splitQty;
    private OperationTaskTypeEnum taskType;

    private OperationTaskStatusEnum taskStatus;
}
