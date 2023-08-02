package com.swms.mdm.api.main.data;


import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public interface IWarehouseMainDataApi {

    void createWarehouse(@Valid WarehouseMainDataDTO warehouseDTO);

    void updateWarehouse(@Valid WarehouseMainDataDTO warehouseDTO);

    WarehouseMainDataDTO getWarehouse(@NotEmpty String warehouseCode);
}
