package com.swms.wms.basic.work_station.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWall;
import com.swms.wms.basic.work_station.domain.entity.WorkStation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkStationTransfer {
    WorkStation toDO(WorkStationDTO workStationDTO);

    WorkStationDTO toDTO(WorkStation workStation);

    List<WorkStationDTO> toDTOs(List<WorkStation> workStations);

    WorkStationDTO toDTO(WorkStation workStation, List<PutWall> putWalls);
}
