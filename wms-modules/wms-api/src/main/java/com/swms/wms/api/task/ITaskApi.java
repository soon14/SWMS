package com.swms.wms.api.task;

import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.task.dto.HandleTaskDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.dto.SealContainerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ITaskApi {

    void createOperationTasks(@NotEmpty List<OperationTaskDTO> operationTaskDTOS);

    List<OperationTaskDTO> queryTasks(@NotEmpty String stationCode, @NotEmpty List<String> containerCodes,
                                      @NotNull OperationTaskTypeEnum taskType);

    void handleTasks(@Valid HandleTaskDTO handleTaskDTO);

    void bindContainer(@Valid BindContainerDTO bindContainerDTO);

    void sealContainer(@Valid SealContainerDTO sealContainerDTO);
}
