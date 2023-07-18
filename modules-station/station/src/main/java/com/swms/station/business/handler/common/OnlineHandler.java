package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineHandler implements IBusinessHandler {

    @Autowired
    private WorkStationService workStationService;

    @Autowired
    private WorkStationManagement workStationManagement;

    @Override
    public void execute(String body, Long workStationId) {

        WorkStation workStation = workStationManagement.getWorkStation(workStationId);
        if (workStation == null) {
            workStation = workStationManagement.initWorkStation(workStationId);
        }

        Preconditions.checkState(workStation.getWorkStationStatus() == WorkStationStatusEnum.OFFLINE);

        workStationService.online(workStationId, WorkStationOperationTypeEnum.valueOf(body));

        workStation.setWorkStationStatus(WorkStationStatusEnum.ONLINE);
        workStation.setOperationType(WorkStationOperationTypeEnum.valueOf(body));
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.ONLINE;
    }
}
