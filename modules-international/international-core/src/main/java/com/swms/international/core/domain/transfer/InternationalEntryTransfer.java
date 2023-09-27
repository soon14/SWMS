package com.swms.international.core.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.internatinal.api.dto.InternationalEntryDTO;
import com.swms.international.core.domain.entity.InternationalEntry;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InternationalEntryTransfer {
    InternationalEntry toDO(InternationalEntryDTO internationalEntryDTO);

    List<InternationalEntryDTO> toDTOs(List<InternationalEntry> internationalEntries);
}
