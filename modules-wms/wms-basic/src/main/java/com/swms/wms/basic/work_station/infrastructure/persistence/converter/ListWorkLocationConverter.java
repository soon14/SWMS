package com.swms.wms.basic.work_station.infrastructure.persistence.converter;

import com.swms.utils.utils.JsonUtils;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import jakarta.persistence.AttributeConverter;

import java.util.List;

@SuppressWarnings("unchecked")
public class ListWorkLocationConverter implements AttributeConverter<List<WorkStationDTO.WorkLocation>, String> {
    @Override
    public String convertToDatabaseColumn(List<WorkStationDTO.WorkLocation> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<WorkStationDTO.WorkLocation> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, WorkStationDTO.WorkLocation.class);
    }
}
