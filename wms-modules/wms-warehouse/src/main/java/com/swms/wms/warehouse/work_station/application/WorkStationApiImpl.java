package com.swms.wms.warehouse.work_station.application;

import com.swms.wms.api.warehouse.IWorkStationApi;
import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.dto.AssignOrdersDTO;
import com.swms.wms.api.warehouse.dto.PutWallSlotDTO;
import com.swms.wms.api.warehouse.dto.ReleasePutWallSlotsDTO;
import com.swms.wms.api.warehouse.dto.WorkStationModelDTO;
import com.swms.wms.warehouse.work_station.domain.service.PutWallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    }

    @Override
    public void releasePutWallSlots(List<ReleasePutWallSlotsDTO> releasePutWallSlotsDTOS) {

    }
}
