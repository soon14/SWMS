package com.swms.mdm.config.infrastructure.persistence.converter;

import com.swms.common.utils.utils.JsonUtils;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListParameterConfigConditionConverter
    implements AttributeConverter<List<ParameterConfigDTO.ParameterConfigCondition>, String> {
    @Override
    public String convertToDatabaseColumn(List<ParameterConfigDTO.ParameterConfigCondition> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<ParameterConfigDTO.ParameterConfigCondition> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, ParameterConfigDTO.ParameterConfigCondition.class);
    }
}
