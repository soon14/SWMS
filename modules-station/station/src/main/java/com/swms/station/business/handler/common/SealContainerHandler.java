package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.TaskService;
import com.swms.utils.utils.JsonUtils;
import com.swms.wms.api.task.dto.SealContainerDTO;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SealContainerHandler implements IBusinessHandler {

    @Autowired
    private WorkStationManagement workStationManagement;

    @Autowired
    private TaskService taskService;

    @Override
    public void execute(String body, String stationCode) {
        WorkStation workStation = workStationManagement.getWorkStation(stationCode);
        Preconditions.checkState(workStation != null);
        Preconditions.checkState(body != null);
        Preconditions.checkState(workStation.getWorkStationStatus() == WorkStationStatusEnum.ONLINE);

        SealContainerDTO sealContainerDTO = JsonUtils.string2Object(body, SealContainerDTO.class);

        taskService.sealContainer(sealContainerDTO);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.SEAL_CONTAINER;
    }
}
