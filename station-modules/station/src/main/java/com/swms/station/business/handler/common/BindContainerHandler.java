package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.TaskService;
import com.swms.utils.utils.JsonUtils;
import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.warehouse.constants.WorkStationStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BindContainerHandler implements IBusinessHandler {

    @Autowired
    private WorkStationManagement workStationManagement;

    @Autowired
    private TaskService taskService;

    @Override
    public void execute(String body, String stationCode) {
        WorkStation workStation = workStationManagement.getWorkStation(stationCode);
        Preconditions.checkState(workStation != null);
        Preconditions.checkState(workStation.getWorkStationStatus() == WorkStationStatusEnum.ONLINE);

        BindContainerDTO bindContainerDTO = JsonUtils.string2Object(body, BindContainerDTO.class);

        taskService.bindContainer(bindContainerDTO);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.BIND_CONTAINER;
    }
}
