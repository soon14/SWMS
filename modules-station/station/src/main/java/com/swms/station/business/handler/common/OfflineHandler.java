package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.WorkStationService;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfflineHandler implements IBusinessHandler<String> {

    @Autowired
    private WorkStationService workStationService;

    @Autowired
    private WorkStationManagement workStationManagement;

    @Override
    public void execute(String body, Long workStationId) {
        WorkStation workStation = workStationManagement.getWorkStation(workStationId);
        Preconditions.checkState(workStation != null);
        Preconditions.checkState(workStation.getWorkStationStatus() != WorkStationStatusEnum.OFFLINE);
        Preconditions.checkState(CollectionUtils.isEmpty(workStation.getOperateTasks()));

        workStationService.offline(workStationId);

        workStationManagement.removeWorkStation(workStationId);
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
