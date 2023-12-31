package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.AssignOrdersDTO;
import com.swms.wms.api.basic.dto.CreatePutWallDTO;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.task.dto.BindContainerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IPutWallApi {

    void save(@Valid PutWallDTO putWallDTO);

    void update(@Valid PutWallDTO putWallDTO);

    void enable(@NotNull Long putWallId);

    void disable(@NotNull Long putWallId);

    void delete(@NotNull Long putWallId);

    void assignOrders(@Valid AssignOrdersDTO assignOrdersDTO);

    void bindContainer(@Valid BindContainerDTO bindContainerDTO);

    void create(@Valid CreatePutWallDTO createPutWallDTO);

    void update(@Valid CreatePutWallDTO putWallDTO);
}
