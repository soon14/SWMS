package com.swms.station.business.handler.event;

import com.swms.wms.api.task.dto.HandleTaskDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty
    private List<Long> taskIds;

    @NotNull
    @Min(1)
    private Integer operatedQty;

    @NotNull
    private HandleTaskDTO.HandleTaskTypeEnum handleTaskType;
}
