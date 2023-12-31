package com.swms.wms.task.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.task.domain.entity.OperationTask;
import com.swms.wms.task.infrastructure.persistence.po.OperationTaskPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OperationTaskPOTransfer {
    List<OperationTask> toOperationTasks(List<OperationTaskPO> selectBatchIds);

    List<OperationTaskPO> toOperationTaskPOs(List<OperationTask> operationTasks);
}
