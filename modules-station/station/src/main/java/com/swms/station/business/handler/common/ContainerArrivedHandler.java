package com.swms.station.business.handler.common;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.business.handler.IBusinessHandler;
import com.swms.station.business.handler.event.ContainerArrivedEvent;
import com.swms.station.business.model.ArrivedContainer;
import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.remote.ContainerService;
import com.swms.station.remote.EquipmentService;
import com.swms.station.remote.TaskService;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerArrivedHandler implements IBusinessHandler<ContainerArrivedEvent> {

    @Autowired
    private ContainerService containerService;

    @Autowired
    private WorkStationService workStationService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EquipmentService equipmentService;

    @Override
    public void execute(ContainerArrivedEvent containerArrivedEvent, Long workStationId) {
        WorkStation workStation = workStationService.getOrThrow(workStationId);

        List<ArrivedContainer> arrivedContainers = containerArrivedEvent.getContainerDetails()
            .stream().map(containerDetail -> {
                ArrivedContainer arrivedContainer = new ArrivedContainer();
                ContainerSpecDTO containerLayoutDTO = containerService.queryContainerLayout(containerDetail.getContainerCode()
                    , workStation.getWarehouseCode(), containerDetail.getFace());
                arrivedContainer.setContainerSpec(containerLayoutDTO);
                arrivedContainer.setFace(containerDetail.getFace());
                arrivedContainer.setLocationCode(containerDetail.getLocationCode());
                arrivedContainer.setRobotCode(containerDetail.getRobotCode());
                arrivedContainer.setRobotType(containerDetail.getRobotType());
                arrivedContainer.setGroupCode(containerDetail.getGroupCode());
                arrivedContainer.setProcessStatus(0);
                arrivedContainer.setContainerCode(containerDetail.getContainerCode());
                arrivedContainer.setWorkLocationCode(containerDetail.getWorkLocationCode());
                arrivedContainer.setWorkLocationType(containerDetail.getWorkLocationType());
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

    @Override
    public Class<ContainerArrivedEvent> getParameterClass() {
        return ContainerArrivedEvent.class;
    }
}
