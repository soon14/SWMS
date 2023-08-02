package com.swms.common.utils.language.converter;

import com.swms.common.utils.language.MultiLanguage;
import com.swms.common.utils.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;

public class MultiLanguageConverter implements AttributeConverter<MultiLanguage, String> {
    @Override
    public String convertToDatabaseColumn(MultiLanguage multiLanguage) {
        return JsonUtils.obj2String(multiLanguage);
    }

    @Override
    public MultiLanguage convertToEntityAttribute(String dbData) {
        return JsonUtils.string2Object(dbData, MultiLanguage.class);
    }
}
