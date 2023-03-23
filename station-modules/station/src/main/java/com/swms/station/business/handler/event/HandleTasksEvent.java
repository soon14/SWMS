package com.swms.station.business.handler.event;

import com.swms.wms.api.task.dto.HandleTaskDTO;
import lombok.Data;

import java.util.List;

@Data
public class HandleTasksEvent {
    private List<Long> taskIds;
    private Integer operatedQty;
    private HandleTaskDTO.HandleTaskTypeEnum handleTaskType;
}
