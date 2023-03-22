package com.swms.wms.api.task.dto;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.Data;

@Data
public class ReportAbnormalTaskDTO {

    private Long taskId;
    private String abnormalQty;

    private OperationTaskTypeEnum taskType;
}
