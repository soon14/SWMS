package com.swms.wms.api.task.event;

import com.swms.domain.event.DomainEvent;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OperationTaskFinishedEvent extends DomainEvent {

    @NotEmpty
    private List<OperationTaskDTO> operationTasks;
}
