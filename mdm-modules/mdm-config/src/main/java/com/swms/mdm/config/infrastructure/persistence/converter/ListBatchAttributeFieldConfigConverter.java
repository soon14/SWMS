package com.swms.mdm.config.infrastructure.persistence.converter;

import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.utils.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListBatchAttributeFieldConfigConverter
    implements AttributeConverter<List<BatchAttributeConfigDTO.BatchAttributeFieldConfigDTO>, String> {
    @Override
    public String convertToDatabaseColumn(List<BatchAttributeConfigDTO.BatchAttributeFieldConfigDTO> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<BatchAttributeConfigDTO.BatchAttributeFieldConfigDTO> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, BatchAttributeConfigDTO.BatchAttributeFieldConfigDTO.class);
    }
}
