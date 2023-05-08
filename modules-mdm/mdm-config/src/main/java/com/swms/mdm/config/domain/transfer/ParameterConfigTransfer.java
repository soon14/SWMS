package com.swms.mdm.config.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.config.domain.entity.ParameterConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ParameterConfigTransfer {
    ParameterConfig toParameterConfig(ParameterConfigDTO parameterConfigDTO);

    List<ParameterConfigDTO> toParameterConfigDTOS(List<ParameterConfig> parameterConfigs);

}
