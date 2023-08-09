package com.swms.wms.stock.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.stock.dto.SkuBatchAttributeDTO;
import com.swms.wms.stock.domain.entity.SkuBatchAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkuBatchAttributeTransfer {
    List<SkuBatchAttributeDTO> toDTOS(List<SkuBatchAttribute> skuBatchAttributes);

    SkuBatchAttributeDTO toDTO(SkuBatchAttribute skuBatchAttribute);
}
