package com.swms.wms.warehouse.work_station.domain.aggregate;

import com.swms.wms.warehouse.work_station.domain.entity.PutWall;
import com.swms.wms.warehouse.work_station.domain.entity.WorkLocation;
import com.swms.wms.warehouse.work_station.domain.entity.WorkStationConfig;
import com.swms.wms.warehouse.work_station.domain.service.PutWallService;
import com.swms.wms.warehouse.work_station.domain.service.WorkLocationService;
import com.swms.wms.warehouse.work_station.domain.service.WorkStationConfigService;
import com.swms.wms.warehouse.work_station.domain.service.WorkStationService;
import lombok.Data;

import java.util.List;

@Data
public class WorkStationAggregate {

    private String stationCode;

    private WorkStationService workStationService;
    private WorkLocationService workLocationService;
    private PutWallService putWallService;
    private WorkStationConfigService workStationConfigService;

    public List<WorkLocation> getWorkLocations() {
        return workLocationService.getWorkLocationsByStationCode(stationCode);
    }

    public void addWorkLocation(WorkLocation workLocation) {
        workLocationService.addWorkLocation(workLocation);
    }

    public void updateWorkLocation(WorkLocation workLocation) {
        workLocationService.updateWorkLocation(workLocation);
    }

    public void removeWorkLocation(Long workLocationId) {
        workLocationService.removeWorkLocation(workLocationId);
    }

    public List<PutWall> getPutWalls() {
        return putWallService.getPutWallsByStationCode(stationCode);
    }

    public void addPutWall(PutWall putWall) {
        putWallService.addPutWall(putWall);
    }

    public void updatePutWall(PutWall putWall) {
        putWallService.updatePutWall(putWall);
    }

    public WorkStationConfig getWorkStationConfig() {
        return workStationConfigService.getWorkStationConfigByStationCode(stationCode);
    }

    public void addWorkStationConfig(WorkStationConfig workStationConfig) {
        workStationConfigService.addWorkStationConfig(workStationConfig);
    }

    public void updateWorkStationConfig(WorkStationConfig workStationConfig) {
        workStationConfigService.updateWorkStationConfig(workStationConfig);
    }
}
