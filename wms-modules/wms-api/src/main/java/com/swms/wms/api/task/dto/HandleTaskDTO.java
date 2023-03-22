package com.swms.wms.api.task.dto;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import lombok.Data;

@Data
public class HandleTaskDTO {

    private Long taskId;
    private Integer operatedQty;
    private OperationTaskStatusEnum taskStatus;
    private OperationTaskStatusEnum taskType;
}
