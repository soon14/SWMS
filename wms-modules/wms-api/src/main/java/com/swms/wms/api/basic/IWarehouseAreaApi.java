package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseAreaDTO;

public interface IWarehouseAreaApi {

    void createWarehouseArea(WarehouseAreaDTO WarehouseAreaDTO);

    void updateWarehouseArea(WarehouseAreaDTO WarehouseAreaDTO);

    void enableWarehouseArea(Long id);

    void disableWarehouseArea(Long id);

    void deleteWarehouseArea(WarehouseAreaDTO WarehouseAreaDTO);

}
