package com.swms.wms.outbound.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.outbound.domain.entity.OutboundWave;
import com.swms.wms.outbound.infrastructure.persistence.po.OutboundWavePO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OutboundWavePOTransfer {
    OutboundWavePO toPO(OutboundWave outboundWave);

    OutboundWave toDO(OutboundWavePO outboundWavePO);

    List<OutboundWave> toDOs(List<OutboundWavePO> outboundWavePOS);

    List<OutboundWavePO> toPOs(List<OutboundWave> outboundWaves);
}
