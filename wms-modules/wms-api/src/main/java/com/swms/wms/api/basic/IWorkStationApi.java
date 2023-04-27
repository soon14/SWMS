package com.swms.wms.api.basic;

import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;

public interface IWorkStationApi {

    void save(WorkStationDTO workStationDTO);

    void update(WorkStationDTO workStationDTO);

    void enable(String stationCode);

    void disable(String stationCode);

    void delete(String stationCode);

    void online(String stationCode, WorkStationOperationTypeEnum operationType);

    void offline(String stationCode);

    void pause(String stationCode);

    void resume(String stationCode);

    WorkStationDTO queryWorkStation(String stationCode);
}
