package com.swms.wms.basic.work_station.application;

import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.AssignOrdersDTO;
import com.swms.wms.api.basic.dto.PutWallSlotDTO;
import com.swms.wms.api.basic.dto.ReleasePutWallSlotsDTO;
import com.swms.wms.api.basic.dto.WorkStationModelDTO;
import com.swms.wms.basic.work_station.domain.service.PutWallService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
public class WorkStationApiImpl implements IWorkStationApi {

    @Autowired
    private PutWallService putWallService;

    @Override
    public void online(String stationCode, WorkStationOperationTypeEnum operationType) {

    }

    @Override
    public void offline(String stationCode) {

    }

    @Override
    public void pause(String stationCode) {

    }

    @Override
    public void resume(String stationCode) {

    }

    @Override
    public WorkStationModelDTO queryWorkStationModel(String stationCode) {
        return null;
    }

    @Override
    public List<PutWallSlotDTO> getPutWallSlots(String stationCode) {
        return putWallService.getPutWallSlotsByStationCode(stationCode);
    }

    @Override
    public void assignOrders(List<AssignOrdersDTO> assignOrdersDTOS) {
        putWallService.assignOrders(assignOrdersDTOS);
    }

    @Override
    public void appendOrders(List<AssignOrdersDTO> assignOrdersDTOS) {
        putWallService.appendOrders(assignOrdersDTOS);
    }

    @Override
    public void releasePutWallSlots(List<ReleasePutWallSlotsDTO> releasePutWallSlotsDTOS) {

    }

    @Override
    public void bindContainer(BindContainerDTO bindContainerDTO) {
        putWallService.bindContainer(bindContainerDTO);
    }
}
