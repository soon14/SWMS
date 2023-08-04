package com.swms.station.business.handler.common;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.remote.RemoteWorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfflineHandler implements IBusinessHandler<String> {

    @Autowired
    private RemoteWorkStationService remoteWorkStationService;

    @Autowired
    private WorkStationService workStationService;

    @Override
    public void execute(String body, Long workStationId) {
        WorkStation workStation = workStationService.getOrThrow(workStationId);

        workStation.offline();
        remoteWorkStationService.offline(workStationId);

        workStationService.delete(workStation);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.OFFLINE;
    }

    @Override
    public Class<String> getParameterClass() {
        return String.class;
    }
}
