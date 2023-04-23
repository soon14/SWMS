package com.swms.wms.api.basic;

import com.swms.wms.api.basic.dto.WarehouseAreaDTO;

public interface IWarehouseAreaApi {

    void save(WarehouseAreaDTO WarehouseAreaDTO);

    void update(WarehouseAreaDTO WarehouseAreaDTO);

    void enable(Long id);

    void disable(Long id);

    void delete(WarehouseAreaDTO WarehouseAreaDTO);

}
