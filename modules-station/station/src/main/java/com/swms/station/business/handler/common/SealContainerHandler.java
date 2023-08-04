package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.TaskService;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.wms.api.task.dto.SealContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SealContainerHandler implements IBusinessHandler<SealContainerDTO> {

    @Autowired
    private WorkStationManagement workStationManagement;

    @Autowired
    private TaskService taskService;

    @Override
    public void execute(SealContainerDTO sealContainerDTO, Long workStationId) {
        WorkStation workStation = workStationManagement.getWorkStation(workStationId);
        Preconditions.checkState(workStation != null);
        Preconditions.checkState(sealContainerDTO != null);
        Preconditions.checkState(workStation.getWorkStationStatus() == WorkStationStatusEnum.ONLINE);

        taskService.sealContainer(sealContainerDTO);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.SEAL_CONTAINER;
    }

    @Override
    public Class<SealContainerDTO> getParameterClass() {
        return SealContainerDTO.class;
    }
}
