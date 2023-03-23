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
    public class HandleTask {
        private Long taskId;
        private Integer requiredQty;
        private Integer operatedQty;
        private Integer abnormalQty;
        private OperationTaskTypeEnum taskType;
        private OperationTaskStatusEnum taskStatus;

        public void setAbnormalQty() {
            if (HandleTaskTypeEnum.REPORT_ABNORMAL.equals(handleTaskType)) {
                this.abnormalQty = this.requiredQty - this.operatedQty;
            }
        }

        public void setTaskStatus() {
            if (HandleTaskTypeEnum.REPORT_ABNORMAL.equals(handleTaskType) && this.requiredQty > this.operatedQty) {
                this.taskStatus = OperationTaskStatusEnum.PROCESSING;
            } else {
                this.taskStatus = OperationTaskStatusEnum.PROCESSED;
            }
        }
    }

    public enum HandleTaskTypeEnum {
        COMPLETE, SPLIT, REPORT_ABNORMAL;
    }
}
