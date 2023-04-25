package com.swms.wms.basic.container.infrastructure.persistence.converter;

import com.swms.utils.utils.JsonUtils;
import com.swms.wms.api.basic.dto.ContainerDTO;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListContainerSlotConverter implements AttributeConverter<List<ContainerDTO.ContainerSlot>, String> {
    @Override
    public String convertToDatabaseColumn(List<ContainerDTO.ContainerSlot> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<ContainerDTO.ContainerSlot> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, ContainerDTO.ContainerSlot.class);
    }
}
