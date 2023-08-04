package com.swms.station.business.handler.common;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.remote.TaskService;
import com.swms.wms.api.task.dto.BindContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BindContainerHandler implements IBusinessHandler<BindContainerDTO> {

    @Autowired
    private WorkStationService workStationService;

    @Autowired
    private TaskService taskService;


    @Override
    public void execute(BindContainerDTO bindContainerDTO, Long workStationId) {
        WorkStation workStation = workStationService.getOrThrow(workStationId);

        workStation.bindContainer(bindContainerDTO);
        taskService.bindContainer(bindContainerDTO);

        workStationService.save(workStation);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.BIND_CONTAINER;
    }

    @Override
    public Class<BindContainerDTO> getParameterClass() {
        return BindContainerDTO.class;
    }
}
