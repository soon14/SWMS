package com.swms.mdm.main.data.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.main.data.domain.entity.SkuMainData;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkuMainDataTransfer {
    SkuMainData toSkuMainData(SkuMainDataDTO skuMainDataDTO);

    SkuMainDataDTO toSkuMainDataDTO(SkuMainData skuMainData);

    List<SkuMainDataDTO> toSkuMainDataDTOS(List<SkuMainData> skuMainDataList);
}
