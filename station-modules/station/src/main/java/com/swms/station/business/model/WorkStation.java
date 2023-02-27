package com.swms.station.business.model;

import com.swms.common.constants.WorkStationOperationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * definition：a place that operators working, only support one station one Operation Type at a time.
 */
@Data
public class WorkStation {

    private String stationCode;

    private String warehouseCode;
    private String warehouseLogicCode;
    private String warehouseAreaCode;

    private WorkStationOperationTypeEnum operationType;

    private List<WorkLocation> workLocations;

    private List<PutWall> putWalls;
}
