package com.swms.wms.basic.work_station.infrastructure.persistence.converter;

import com.swms.common.utils.utils.JsonUtils;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListWorkStationOperationTypeConverter implements AttributeConverter<List<WorkStationOperationTypeEnum>, String> {
    @Override
    public String convertToDatabaseColumn(List<WorkStationOperationTypeEnum> attribute) {
        return JsonUtils.obj2String(attribute);
    }

    @Override
    public List<WorkStationOperationTypeEnum> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, WorkStationOperationTypeEnum.class);
    }
}
