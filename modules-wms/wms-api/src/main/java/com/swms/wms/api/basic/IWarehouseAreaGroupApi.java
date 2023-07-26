package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseAreaGroupDTO;

public interface IWarehouseAreaGroupApi {

    void save(WarehouseAreaGroupDTO warehouseAreaGroupDTO);

    void update(WarehouseAreaGroupDTO warehouseAreaGroupDTO);

    void enable(Long id);

    void disable(Long id);

    void delete(Long id);

}
