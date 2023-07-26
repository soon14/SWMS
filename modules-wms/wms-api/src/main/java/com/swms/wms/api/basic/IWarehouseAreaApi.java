package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseAreaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IWarehouseAreaApi {

    void save(@Valid WarehouseAreaDTO warehouseAreaDTO);

    void update(@Valid WarehouseAreaDTO warehouseAreaDTO);

    void enable(@NotNull Long id);

    void disable(@NotNull Long id);

    void delete(@Valid WarehouseAreaDTO warehouseAreaDTO);

    WarehouseAreaDTO getById(Long warehouseAreaId);
}
