package com.swms.wms.basic.work_station.infrastructure.persistence.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.basic.work_station.domain.entity.PutWall;
import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallPO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PutWallPOTransfer {
    PutWallPO toPO(PutWall putWall);

    List<PutWallPO> toPOs(List<PutWall> putWalls);

    PutWall toDO(PutWallPO putWallPO);

    List<PutWall> toDOs(List<PutWallPO> putWallPOS);
}
