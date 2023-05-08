package com.swms.station.business.handler.event;

import com.swms.wms.api.task.dto.HandleTaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HandleTasksEvent {
    private List<Long> taskIds;
    private Integer operatedQty;
    private HandleTaskDTO.HandleTaskTypeEnum handleTaskType;
}
