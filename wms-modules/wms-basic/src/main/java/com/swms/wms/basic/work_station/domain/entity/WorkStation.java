package com.swms.wms.basic.work_station.domain.entity;

import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.constants.WorkStationStatusEnum;
import lombok.Data;

@Data
public class WorkStation {

    private Long id;
    private String stationCode;

    private WorkStationStatusEnum workStationStatus;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;

    private WorkStationOperationTypeEnum operationType;
}
