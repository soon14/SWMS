package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.remote.TaskService;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.wms.api.task.dto.SealContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SealContainerHandler implements IBusinessHandler<SealContainerDTO> {

    @Autowired
    private WorkStationService workStationService;

    @Autowired
    private TaskService taskService;

    @Override
    public void execute(SealContainerDTO sealContainerDTO, Long workStationId) {
        WorkStation workStation = workStationService.getOrThrow(workStationId);

        workStation.sealContainer(sealContainerDTO);
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
