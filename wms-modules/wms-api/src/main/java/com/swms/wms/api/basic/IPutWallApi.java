package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.AssignOrdersDTO;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.basic.dto.ReleasePutWallSlotsDTO;
import com.swms.wms.api.task.dto.BindContainerDTO;

import java.util.List;

public interface IPutWallApi {

    void save(PutWallDTO putWallDTO);

    void update(PutWallDTO putWallDTO);

    List<PutWallDTO.PutWallSlot> getPutWallSlots(String stationCode);

    void assignOrders(List<AssignOrdersDTO> assignOrdersDTOS);

    void appendOrders(List<AssignOrdersDTO> assignOrdersDTOS);

    void releasePutWallSlots(List<ReleasePutWallSlotsDTO> releasePutWallSlotsDTOS);

    void bindContainer(BindContainerDTO bindContainerDTO);
}
