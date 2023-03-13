package com.swms.wms.warehouse.work_station.application;

import com.swms.wms.api.warehouse.IWorkStationApi;
import com.swms.wms.api.warehouse.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.warehouse.dto.AssignOrdersDTO;
import com.swms.wms.api.warehouse.dto.ReleasePutWallSlotsDTO;
import com.swms.wms.api.warehouse.dto.WorkStationModelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkStationApiImpl implements IWorkStationApi {

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
    public void assignOrders(List<AssignOrdersDTO> assignOrdersDTOS) {

    }

    @Override
    public void releasePutWallSlots(List<ReleasePutWallSlotsDTO> releasePutWallSlotsDTOS) {

    }
}
