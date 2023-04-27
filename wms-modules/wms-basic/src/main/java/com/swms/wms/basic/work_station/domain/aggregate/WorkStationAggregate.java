package com.swms.wms.basic.work_station.domain.aggregate;

import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.basic.work_station.domain.service.WorkLocationService;
import com.swms.wms.basic.work_station.domain.service.WorkStationConfigService;
import com.swms.wms.basic.work_station.domain.service.WorkStationService;
import lombok.Data;

@Data
public class WorkStationAggregate {

    private String stationCode;

    private WorkStationService workStationService;
    private WorkLocationService workLocationService;
    private WorkStationConfigService workStationConfigService;

    public WorkStationDTO getWorkStationMode() {

//        WorkStation workStation = workStationService.getStation(stationCode);
//        List<WorkLocation> workLocations = workLocationService.getWorkLocationsByStationCode(stationCode);
//        List<PutWall> putWalls = putWallService.getPutWallsByStationCode(stationCode);
//        WorkStationConfig workStationConfig = workStationConfigService.getWorkStationConfigByStationCode(stationCode);

        return WorkStationDTO.builder().build();
    }

}
