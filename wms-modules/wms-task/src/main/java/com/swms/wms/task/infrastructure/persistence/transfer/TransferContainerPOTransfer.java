package com.swms.wms.task.infrastructure.persistence.transfer;

import com.swms.wms.task.domain.entity.TransferContainer;
import com.swms.wms.task.infrastructure.persistence.po.TransferContainerPO;
import com.swms.wms.task.infrastructure.persistence.po.TransferContainerTaskRelationPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = ALWAYS,
        nullValueMappingStrategy = RETURN_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransferContainerPOTransfer {
    TransferContainerPO toPO(TransferContainer transferContainer);

    List<TransferContainerTaskRelationPO> toPOs(List<TransferContainer.TransferContainerTaskRelation> transferContainerTasks);
}
