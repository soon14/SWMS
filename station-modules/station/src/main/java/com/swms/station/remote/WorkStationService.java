package com.swms.station.remote;

import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.IWorkStationApi;
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

    public Object queryWorkStation(String stationCode) {
        return workStationApi.queryWorkStation(stationCode);
    }

    public void setWorkStationApi(IWorkStationApi iWorkStationApi) {
        this.workStationApi = iWorkStationApi;
    }

}
