package com.swms.mdm.config.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.config.domain.entity.Dictionary;
import com.swms.mdm.config.infrastructure.persistence.po.DictionaryPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DictionaryPOTransfer {
    DictionaryPO toPO(Dictionary dictionary);

    Dictionary toDO(DictionaryPO id);

    List<Dictionary> toDOS(List<DictionaryPO> dictionaryPOS);
}
