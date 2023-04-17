package com.swms.wms.task.infrastructure.persistence.po;

import lombok.Data;

@Data
public class TransferContainerTaskRelationPO {

    private Long id;
    private Long transferContainerId;
    private Long operationTaskId;
}
