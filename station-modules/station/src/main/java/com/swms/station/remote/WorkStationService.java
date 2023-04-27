package com.swms.station.remote;

import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class WorkStationService {

    @DubboReference
    private IWorkStationApi workStationApi;

    public void online(String stationCode, WorkStationOperationTypeEnum operationType) {
        workStationApi.online(stationCode, operationType);
    }

    public void offline(String stationCode) {
        workStationApi.offline(stationCode);
    }

    public void pause(String stationCode) {
        workStationApi.pause(stationCode);
    }

    public void resume(String stationCode) {
        workStationApi.resume(stationCode);
    }

    public WorkStationDTO queryWorkStation(String stationCode) {
        return workStationApi.queryWorkStation(stationCode);
    }

    public void setWorkStationApi(IWorkStationApi iWorkStationApi) {
        this.workStationApi = iWorkStationApi;
    }

}
