package com.swms.wms.task.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.task.domain.entity.OperationTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OperationTaskTransfer {

    @Mapping(source = "id", target = "taskId")
    List<OperationTaskDTO> toOperationTaskDTOS(List<OperationTask> operationTasks);

    @Mapping(source = "taskId", target = "id")
    List<OperationTask> toOperationTasks(List<OperationTaskDTO> operationTasks);
}
