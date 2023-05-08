package com.swms.wms.task.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TransferContainer {

    private Long id;
    private String transferContainerCode;
    private String stationCode;
    private String putWallSlotCode;

    private Integer containerIndex;
    private Integer total;

    private String destination;

    private List<TransferContainerTaskRelation> transferContainerTasks;

    @Data
    @Builder
    public static class TransferContainerTaskRelation {

        private Long id;
        private Long transferContainerId;
        private Long operationTaskId;
    }
}
