package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.common.constants.WorkStationOperationTypeEnum;
import com.swms.common.constants.WorkStationStatusEnum;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.wms.api.warehouse.IWorkStationApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineHandler implements IBusinessHandler {

    @DubboReference
    private IWorkStationApi iWorkStationApi;

    @Autowired
    private WorkStationManagement workStationManagement;

    @Override
    public void execute(String body, String stationCode) {

        WorkStation workStation = workStationManagement.getWorkStation(stationCode);
        if (workStation == null) {
            workStation = workStationManagement.initWorkStation(stationCode);
        }

        Preconditions.checkState(workStation.getWorkStationStatus() == WorkStationStatusEnum.OFFLINE);

        iWorkStationApi.online(stationCode, WorkStationOperationTypeEnum.valueOf(body));

        workStation.setWorkStationStatus(WorkStationStatusEnum.ONLINE);
        workStation.setOperationType(WorkStationOperationTypeEnum.valueOf(body));
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.ONLINE;
    }
}
