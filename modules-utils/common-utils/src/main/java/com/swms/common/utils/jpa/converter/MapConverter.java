package com.swms.common.utils.jpa.converter;

import com.swms.common.utils.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;

import java.util.Map;

public class MapConverter implements AttributeConverter<Map<String, Object>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2MapObject(dbData);
    }
}
