package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseConfigDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public interface IWarehouseConfigApi {

    void save(@Valid WarehouseConfigDTO warehouseConfigDTO);

    void update(@Valid WarehouseConfigDTO warehouseConfigDTO);

    WarehouseConfigDTO getWarehouseConfig(@NotEmpty String warehouseCode);
}
