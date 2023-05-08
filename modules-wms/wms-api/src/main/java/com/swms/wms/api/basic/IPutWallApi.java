package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.AssignOrdersDTO;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.basic.dto.ReleasePutWallSlotsDTO;
import com.swms.wms.api.task.dto.BindContainerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface IPutWallApi {

    void save(@Valid PutWallDTO putWallDTO);

    void update(@Valid PutWallDTO putWallDTO);

    void enable(@NotEmpty String putWallCode);

    void disable(@NotEmpty String putWallCode);

    void delete(@NotEmpty String putWallCode);

    List<PutWallDTO.PutWallSlot> getPutWallSlots(@NotEmpty String stationCode);

    void assignOrders(@NotEmpty List<AssignOrdersDTO> assignOrdersDTOS);

    void appendOrders(@NotEmpty List<AssignOrdersDTO> assignOrdersDTOS);

    void releasePutWallSlots(@NotEmpty List<ReleasePutWallSlotsDTO> releasePutWallSlotsDTOS);

    void bindContainer(@Valid BindContainerDTO bindContainerDTO);
}
