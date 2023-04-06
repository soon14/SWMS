package com.swms.wms.basic.work_station.domain.aggregate;

import com.swms.wms.api.warehouse.dto.WorkStationModelDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWall;
import com.swms.wms.basic.work_station.domain.entity.WorkLocation;
import com.swms.wms.basic.work_station.domain.entity.WorkStation;
import com.swms.wms.basic.work_station.domain.entity.WorkStationConfig;
import com.swms.wms.basic.work_station.domain.service.PutWallService;
import com.swms.wms.basic.work_station.domain.service.WorkLocationService;
import com.swms.wms.basic.work_station.domain.service.WorkStationConfigService;
import com.swms.wms.basic.work_station.domain.service.WorkStationService;
import lombok.Data;

import java.util.List;

@Data
public class WorkStationAggregate {

    private String stationCode;

    private WorkStationService workStationService;
    private WorkLocationService workLocationService;
    private PutWallService putWallService;
    private WorkStationConfigService workStationConfigService;

    public WorkStationModelDTO getWorkStationMode() {

        WorkStation workStation = workStationService.getStation(stationCode);
        List<WorkLocation> workLocations = workLocationService.getWorkLocationsByStationCode(stationCode);
        List<PutWall> putWalls = putWallService.getPutWallsByStationCode(stationCode);
        WorkStationConfig workStationConfig = workStationConfigService.getWorkStationConfigByStationCode(stationCode);

        return WorkStationModelDTO.builder().build();
    }

}
