package com.swms.wms.api.task.dto;

import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class HandleTaskDTO {

    @NotNull
    private HandleTaskTypeEnum handleTaskType;

    @NotEmpty
    private List<HandleTask> handleTasks;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HandleTask {

        // is a redundancy
        @NotNull
        private HandleTaskTypeEnum handleTaskType;
        @NotNull
        private Long taskId;
        @NotNull
        private Integer requiredQty;
        @NotNull
        private Integer operatedQty;
        private Integer abnormalQty;
        @NotNull
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
