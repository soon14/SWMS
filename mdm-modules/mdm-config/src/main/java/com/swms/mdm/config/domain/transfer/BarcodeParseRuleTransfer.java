package com.swms.mdm.config.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.mdm.config.domain.entity.BarcodeParseRule;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BarcodeParseRuleTransfer {

    BarcodeParseRule toBarcodeParseRule(BarcodeParseRuleDTO barcodeParseRuleDTO);
}
