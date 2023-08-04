package com.swms.station.business.handler.common;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.remote.RemoteWorkStationService;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineHandler implements IBusinessHandler<String> {

    @Autowired
    private RemoteWorkStationService remoteWorkStationService;

    @Autowired
    private WorkStationService workStationService;

    @Override
    public void execute(String operationType, Long workStationId) {

        WorkStation workStation = workStationService.getWorkStation(workStationId);
        if (workStation == null) {
            workStation = workStationService.initWorkStation(workStationId);
        }

        workStation.online(WorkStationOperationTypeEnum.valueOf(operationType));
        remoteWorkStationService.online(workStationId, WorkStationOperationTypeEnum.valueOf(operationType));

        workStationService.save(workStation);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.ONLINE;
    }

    @Override
    public Class<String> getParameterClass() {
        return String.class;
    }
}
