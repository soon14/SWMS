package com.swms.wms.api.basic.dto;

import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkStationModelDTO {

    private String stationCode;

    private WorkStationStatusEnum workStationStatus;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;

    private WorkStationOperationTypeEnum operationType;

    private List<WorkLocationDTO> workLocations;
    private List<PutWallDTO> putWalls;
    private WorkStationConfigDTO workStationConfig;
}
