package com.swms.utils.jpa.converter;

import com.swms.utils.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListLongConverter implements AttributeConverter<List<Long>, String> {
    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, Long.class);
    }
}
