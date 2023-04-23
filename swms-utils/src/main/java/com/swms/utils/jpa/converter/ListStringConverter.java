package com.swms.utils.jpa.converter;

import com.swms.utils.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListStringConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, String.class);
    }
}
