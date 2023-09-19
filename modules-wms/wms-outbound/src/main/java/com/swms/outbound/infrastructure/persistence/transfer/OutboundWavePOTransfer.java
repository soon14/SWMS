package com.swms.outbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.infrastructure.persistence.po.OutboundWavePO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OutboundWavePOTransfer {
    OutboundWavePO toPO(OutboundWave outboundWave);

    OutboundWave toDO(OutboundWavePO outboundWavePO);
}
