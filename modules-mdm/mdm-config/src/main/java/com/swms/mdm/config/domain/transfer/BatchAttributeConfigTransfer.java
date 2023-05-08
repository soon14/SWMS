package com.swms.mdm.config.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.mdm.config.domain.entity.BatchAttributeConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BatchAttributeConfigTransfer {

    BatchAttributeConfig toBatchAttributeConfig(BatchAttributeConfigDTO batchAttributeConfigDTO);

    BatchAttributeConfigDTO toBatchAttributeConfigDTO(Object batchAttributeConfig);
}
