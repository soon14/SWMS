package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseGroupDTO;

public interface IWarehouseGroupApi {

    void createWarehouseGroup(WarehouseGroupDTO WarehouseGroupDTO);

    void updateWarehouseGroup(WarehouseGroupDTO WarehouseGroupDTO);

    void enableWarehouseGroup(Long id);

    void disableWarehouseGroup(Long id);

    void deleteWarehouseGroup(WarehouseGroupDTO WarehouseGroupDTO);

}
