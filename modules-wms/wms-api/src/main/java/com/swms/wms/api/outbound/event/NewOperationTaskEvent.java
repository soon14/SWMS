package com.swms.wms.api.outbound.event;

import com.swms.domain.event.DomainEvent;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class NewOperationTaskEvent extends DomainEvent {

    private List<OperationTaskDTO> operationTasks;
}
