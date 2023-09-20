package com.swms.outbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.outbound.domain.entity.OutboundPreAllocatedRecord;
import com.swms.outbound.infrastructure.persistence.po.OutboundPreAllocatedRecordPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OutboundPreAllocatedRecordPOTransfer {
    List<OutboundPreAllocatedRecordPO> toPOs(List<OutboundPreAllocatedRecord> planPreAllocatedRecords);

    List<OutboundPreAllocatedRecord> toDOs(List<OutboundPreAllocatedRecordPO> recordPOS);
}
