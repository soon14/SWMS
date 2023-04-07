package com.swms.wms.api.basic;

import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.AssignOrdersDTO;
import com.swms.wms.api.basic.dto.PutWallSlotDTO;
import com.swms.wms.api.basic.dto.ReleasePutWallSlotsDTO;
import com.swms.wms.api.basic.dto.WorkStationModelDTO;

import java.util.List;

public interface IWorkStationApi {

    void online(String stationCode, WorkStationOperationTypeEnum operationType);

    void offline(String stationCode);

    void pause(String stationCode);

    void resume(String stationCode);

    WorkStationModelDTO queryWorkStationModel(String stationCode);

    List<PutWallSlotDTO> getPutWallSlots(String stationCode);


    void assignOrders(List<AssignOrdersDTO> assignOrdersDTOS);

    void appendOrders(List<AssignOrdersDTO> assignOrdersDTOS);

    void releasePutWallSlots(List<ReleasePutWallSlotsDTO> releasePutWallSlotsDTOS);

    void bindContainer(BindContainerDTO bindContainerDTO);
}
