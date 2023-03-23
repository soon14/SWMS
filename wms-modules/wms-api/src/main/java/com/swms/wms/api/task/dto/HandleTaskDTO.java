package com.swms.wms.api.task.dto;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HandleTaskDTO {

    private HandleTaskTypeEnum handleTaskType;

    private List<HandleTask> handleTasks;

    @Data
    @Builder
    public static class HandleTask {
        private Long taskId;
        private Integer requiredQty;
        private Integer operatedQty;
        private Integer abnormalQty;
        private OperationTaskTypeEnum taskType;
    }

    public enum HandleTaskTypeEnum {
        COMPLETE, SPLIT, REPORT_ABNORMAL;
    }
}
