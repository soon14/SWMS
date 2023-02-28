package com.swms.station.business.handler.common;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineHandler implements IBusinessHandler {

    @Autowired
    private WorkStationManagement workStationManagement;

    @Override
    public Object execute(String body, String stationCode) {

        WorkStation workStation = workStationManagement.getWorkStation(stationCode);


        return null;
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.ONLINE;
    }
}
