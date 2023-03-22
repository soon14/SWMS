package com.swms.wms.task.domain.aggregate;

import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.domain.entity.TransferContainerTaskRelation;
import lombok.Data;

import java.util.List;

@Data
public class TransferContainerAggregate {

    private Long id;
    private String transferContainerCode;
    private String stationCode;
    private String putWallSlot;

    private Integer index;
    private Integer total;

    private String destination;

    private List<OperationTask> operationTasks;

    private List<TransferContainerTaskRelation> transferContainerTaskRelations;


}
