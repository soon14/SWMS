package com.swms.international.core.domain.convertor;

import com.swms.common.utils.utils.JsonUtils;
import com.swms.internatinal.api.dto.InternationalEntryDTO;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListLanguageValueMappingConverter implements AttributeConverter<List<InternationalEntryDTO.LanguageValueMapping>, String> {
    @Override
    public String convertToDatabaseColumn(List<InternationalEntryDTO.LanguageValueMapping> languageValueMappings) {
        return JsonUtils.obj2String(languageValueMappings);
    }

    @Override
    public List<InternationalEntryDTO.LanguageValueMapping> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, InternationalEntryDTO.LanguageValueMapping.class);
    }
}
