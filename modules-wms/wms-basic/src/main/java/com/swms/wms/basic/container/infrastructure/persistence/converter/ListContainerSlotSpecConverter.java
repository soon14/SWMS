package com.swms.wms.basic.container.infrastructure.persistence.converter;

import com.swms.utils.utils.JsonUtils;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListContainerSlotSpecConverter implements AttributeConverter<List<ContainerSpecDTO.ContainerSlotSpec>, String> {
    @Override
    public String convertToDatabaseColumn(List<ContainerSpecDTO.ContainerSlotSpec> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<ContainerSpecDTO.ContainerSlotSpec> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, ContainerSpecDTO.ContainerSlotSpec.class);
    }
}
