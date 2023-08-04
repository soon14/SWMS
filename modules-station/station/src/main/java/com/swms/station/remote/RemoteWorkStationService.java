package com.swms.station.remote;

import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class RemoteWorkStationService {

    @DubboReference
    private IWorkStationApi workStationApi;

    public void online(Long workStationId, WorkStationOperationTypeEnum operationType) {
        workStationApi.online(workStationId, operationType);
    }

    public void offline(Long workStationId) {
        workStationApi.offline(workStationId);
    }

    public void pause(Long workStationId) {
        workStationApi.pause(workStationId);
    }

    public void resume(Long workStationId) {
        workStationApi.resume(workStationId);
    }

    public WorkStationDTO queryWorkStation(Long workStationId) {
        return workStationApi.queryWorkStation(workStationId);
    }

    public void setWorkStationApi(IWorkStationApi iWorkStationApi) {
        this.workStationApi = iWorkStationApi;
    }

}
