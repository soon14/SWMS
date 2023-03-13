package com.swms.wms.api.warehouse;

import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.dto.WorkStationModelDTO;

public interface IWorkStationApi {

    void online(String stationCode, WorkStationOperationTypeEnum operationType);

    void offline(String stationCode);

    void pause(String stationCode);

    void resume(String stationCode);

    void callRobot(String stationCode);

    WorkStationModelDTO queryWorkStationModel(String stationCode);

}
