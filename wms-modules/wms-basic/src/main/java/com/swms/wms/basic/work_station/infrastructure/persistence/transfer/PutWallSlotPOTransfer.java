package com.swms.wms.basic.work_station.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWall;
import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallPO;
import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallSlotPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PutWallSlotPOTransfer {
    List<PutWallSlotPO> toPOS(List<PutWallDTO.PutWallSlot> putWallSlots);

    List<PutWallDTO.PutWallSlot> toPutWallSlots(List<PutWallSlotPO> putWallSlotPOS);
}