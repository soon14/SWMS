package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseLogicDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IWarehouseLogicApi {

    void save(@Valid WarehouseLogicDTO warehouseLogicDTO);

    void update(@Valid WarehouseLogicDTO warehouseLogicDTO);

    void enable(@NotNull Long id);

    void disable(@NotNull Long id);

    void delete(@Valid WarehouseLogicDTO warehouseLogicDTO);

}
