package com.swms.wms.basic.warehouse.infrastructure.persistence.converter;

import com.swms.mdm.api.config.dto.WarehouseMainDataConfigDTO;
import com.swms.common.utils.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;

public class WarehouseMainDataConfigDTOConverter implements AttributeConverter<WarehouseMainDataConfigDTO, String> {

    @Override
    public String convertToDatabaseColumn(WarehouseMainDataConfigDTO attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public WarehouseMainDataConfigDTO convertToEntityAttribute(String dbData) {
        return JsonUtils.string2Object(dbData, WarehouseMainDataConfigDTO.class);
    }
}
