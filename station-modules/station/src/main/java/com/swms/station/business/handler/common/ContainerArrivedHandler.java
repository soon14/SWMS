package com.swms.station.business.handler.common;

import com.google.common.base.Preconditions;
import com.swms.station.remote.EquipmentService;
import com.swms.station.remote.TaskService;
import com.swms.utils.utils.JsonUtils;
import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.handler.event.ContainerArrivedEvent;
import com.swms.station.business.model.ArrivedContainer;
import com.swms.station.business.model.WorkStation;
import com.swms.station.business.model.WorkStationManagement;
import com.swms.station.remote.ContainerService;
import com.swms.wms.api.basic.dto.ContainerLayoutDTO;
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

    @Autowired
    private TaskService taskService;

    @Autowired
    private EquipmentService equipmentService;

    @Override
    public void execute(String body, String stationCode) {
        WorkStation workStation = workStationManagement.getWorkStation(stationCode);
        Preconditions.checkState(workStation != null);

        List<ContainerArrivedEvent> containerArrivedEvents = JsonUtils.string2List(body, ContainerArrivedEvent.class);
        List<ArrivedContainer> arrivedContainers = containerArrivedEvents.stream().map(containerArrivedEvent -> {
            ArrivedContainer arrivedContainer = new ArrivedContainer();
            ContainerLayoutDTO containerLayoutDTO = containerService.queryContainerLayout(containerArrivedEvent.getContainerCode()
                , containerArrivedEvent.getFace());
            arrivedContainer.setContainerLayout(containerLayoutDTO);
            arrivedContainer.setFace(containerArrivedEvent.getFace());
            arrivedContainer.setLocationCode(containerArrivedEvent.getLocationCode());
            arrivedContainer.setRobotCode(containerArrivedEvent.getRobotCode());
            arrivedContainer.setRobotType(containerArrivedEvent.getRobotType());
            arrivedContainer.setGroupCode(containerArrivedEvent.getGroupCode());
            arrivedContainer.setProcessStatus(0);
            arrivedContainer.setContainerCode(containerArrivedEvent.getContainerCode());
            arrivedContainer.setWorkLocationCode(containerArrivedEvent.getWorkLocationCode());
            arrivedContainer.setWorkLocationType(containerArrivedEvent.getWorkLocationType());
            arrivedContainer.setRotationAngle();
            return arrivedContainer;
        }).toList();

        workStation.addArrivedContainers(arrivedContainers);

        if (CollectionUtils.isNotEmpty(workStation.getOperateTasks())) {
            return;
        }

        workStation.setArrivedContainersOnLocation(arrivedContainers);

        workStation.handleUndoContainers(taskService, equipmentService);
    }

    @Override
    public ApiCodeEnum getApiCode() {
        return ApiCodeEnum.CONTAINER_ARRIVED;
    }
}
