package com.swms.wms.api.warehouse;

import com.swms.common.constants.WorkStationOperationTypeEnum;

public interface IWorkStationApi {

    void online(String stationCode, WorkStationOperationTypeEnum operationType);

    void offline(String stationCode);

    void pause(String stationCode);

    void resume(String stationCode);

    void callRobot(String stationCode);

    Object queryWorkStation(String stationCode);

}
