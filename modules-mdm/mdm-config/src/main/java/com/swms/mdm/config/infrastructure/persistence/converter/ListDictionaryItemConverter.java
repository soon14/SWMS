package com.swms.mdm.config.infrastructure.persistence.converter;

import com.swms.mdm.config.domain.entity.Dictionary;
import com.swms.utils.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListDictionaryItemConverter
    implements AttributeConverter<List<Dictionary.DictionaryItem>, String> {
    @Override
    public String convertToDatabaseColumn(List<Dictionary.DictionaryItem> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<Dictionary.DictionaryItem> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, Dictionary.DictionaryItem.class);
    }
}
