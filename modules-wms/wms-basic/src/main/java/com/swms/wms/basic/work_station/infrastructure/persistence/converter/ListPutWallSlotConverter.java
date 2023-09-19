package com.swms.wms.basic.work_station.infrastructure.persistence.converter;

import com.swms.common.utils.utils.JsonUtils;
import com.swms.wms.api.basic.dto.PutWallDTO;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class ListPutWallSlotConverter implements AttributeConverter<List<PutWallDTO.PutWallSlot>, String> {
    @Override
    public String convertToDatabaseColumn(List<PutWallDTO.PutWallSlot> putWallSlots) {
        return JsonUtils.obj2String(putWallSlots);
    }

    @Override
    public List<PutWallDTO.PutWallSlot> convertToEntityAttribute(String dbData) {
        return JsonUtils.string2List(dbData, PutWallDTO.PutWallSlot.class);
    }
}
