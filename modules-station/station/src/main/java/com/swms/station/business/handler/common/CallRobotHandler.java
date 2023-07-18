package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.EquipmentService;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallRobotHandler implements IBusinessHandler {

    @Autowired
    private WorkStationManagement workStationManagement;

    @Autowired
    private EquipmentService equipmentService;

    @Override
    public void execute(String body, Long workStationId) {
        WorkStation workStation = workStationManagement.getWorkStation(workStationId);
        Preconditions.checkState(workStation != null);
        Preconditions.checkState(workStation.getWorkStationStatus() == WorkStationStatusEnum.ONLINE);

        equipmentService.callRobot(workStationId);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.CALL_ROBOT;
    }
}
