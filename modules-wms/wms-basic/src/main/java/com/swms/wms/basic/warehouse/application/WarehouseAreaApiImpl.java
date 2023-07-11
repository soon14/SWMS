package com.swms.wms.basic.warehouse.application;

import com.swms.wms.api.basic.IWarehouseAreaApi;
import com.swms.wms.api.basic.dto.WarehouseAreaDTO;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseAreaRepository;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseAreaTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class WarehouseAreaApiImpl implements IWarehouseAreaApi {

    @Autowired
    private WarehouseAreaRepository warehouseAreaRepository;

    @Autowired
    private WarehouseAreaTransfer warehouseAreaTransfer;

    @Override
    public void save(WarehouseAreaDTO warehouseAreaDTO) {
        warehouseAreaRepository.save(warehouseAreaTransfer.toDO(warehouseAreaDTO));
    }

    @Override
    public void update(WarehouseAreaDTO warehouseAreaDTO) {
        warehouseAreaRepository.save(warehouseAreaTransfer.toDO(warehouseAreaDTO));
    }

    @Override
    public void enable(Long id) {

    }

    @Override
    public void disable(Long id) {

    }

    @Override
    public void delete(WarehouseAreaDTO WarehouseAreaDTO) {

    }
}
