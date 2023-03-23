package com.swms.wms.task.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferContainerTaskRelation {

    private Long id;
    private Long transferContainerId;
    private Long operationTaskId;
}
