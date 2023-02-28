package com.swms.station.business.model;

import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.common.constants.WorkStationStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * definition：a place that operators working, only support one station one Operation Type at a time.
 */
@Data
public class WorkStation {

    private String stationCode;

    private WorkStationStatusEnum workStationStatus;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;

    private WorkStationOperationTypeEnum operationType;

    private List<WorkLocation> workLocations;

    private List<PutWall> putWalls;

    private List<OperateTask> operateTasks;

    private WorkStationConfig workStationConfig;

}
