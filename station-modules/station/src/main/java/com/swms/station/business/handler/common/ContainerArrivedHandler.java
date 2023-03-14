package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.utils.utils.JsonUtils;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.handler.event.ContainerArrivedEvent;
import com.swms.station.business.model.ArrivedContainer;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.ContainerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerArrivedHandler implements IBusinessHandler {

    @Autowired
    private ContainerService containerService;

    @Autowired
    private WorkStationManagement workStationManagement;

    @Override
    public void execute(String body, String stationCode) {
        WorkStation workStation = workStationManagement.getWorkStation(stationCode);
        Preconditions.checkState(workStation != null);

        List<ContainerArrivedEvent> containerArrivedEvents = JsonUtils.string2List(body, ContainerArrivedEvent.class);
        List<ArrivedContainer> arrivedContainers = containerArrivedEvents.stream().map(containerArrivedEvent -> {
            ArrivedContainer arrivedContainer = containerService.queryContainer(containerArrivedEvent.getContainerCode());
            arrivedContainer.setFace(containerArrivedEvent.getFace());
            arrivedContainer.setLocationCode(containerArrivedEvent.getLocationCode());
            arrivedContainer.setRobotCode(containerArrivedEvent.getRobotCode());
            arrivedContainer.setRobotType(containerArrivedEvent.getRobotType());
            arrivedContainer.setGroupCode(containerArrivedEvent.getGroupCode());
            arrivedContainer.setProcessStatus(0);
            return arrivedContainer;
        }).toList();

        if (CollectionUtils.isEmpty(workStation.getArrivedContainers())) {
            workStation.setArrivedContainers(arrivedContainers);
        } else {
            workStation.getArrivedContainers().addAll(arrivedContainers);
        }

        if (CollectionUtils.isNotEmpty(workStation.getOperateTasks())) {
            return;
        }

        workStation.handleUndoContainers();
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.RESUME;
    }
}
