package com.swms.wms.task.domain.entity;

import lombok.Data;

@Data
public class TransferContainerTaskRelation {

    private Long id;
    private Long transferContainerId;
    private Long operationTaskId;
}
