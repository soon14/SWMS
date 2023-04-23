package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseGroupDTO;

public interface IWarehouseGroupApi {

    void save(WarehouseGroupDTO WarehouseGroupDTO);

    void update(WarehouseGroupDTO WarehouseGroupDTO);

    void enable(Long id);

    void disable(Long id);

    void delete(WarehouseGroupDTO WarehouseGroupDTO);

}
