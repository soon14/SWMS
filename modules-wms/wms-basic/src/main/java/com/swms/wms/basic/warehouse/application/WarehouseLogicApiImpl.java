package com.swms.wms.basic.warehouse.application;

import com.swms.wms.api.basic.IWarehouseLogicApi;
import com.swms.wms.api.basic.dto.WarehouseLogicDTO;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseLogicRepository;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseLogicTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class WarehouseLogicApiImpl implements IWarehouseLogicApi {

    @Autowired
    private WarehouseLogicRepository warehouseLogicRepository;

    @Autowired
    private WarehouseLogicTransfer warehouseLogicTransfer;

    @Override
    public void save(WarehouseLogicDTO warehouseLogicDTO) {
        warehouseLogicRepository.save(warehouseLogicTransfer.toDO(warehouseLogicDTO));
    }

    @Override
    public void update(WarehouseLogicDTO warehouseLogicDTO) {
        warehouseLogicRepository.save(warehouseLogicTransfer.toDO(warehouseLogicDTO));
    }

    @Override
    public void enable(Long id) {

    }

    @Override
    public void disable(Long id) {

    }

    @Override
    public void delete(WarehouseLogicDTO WarehouseLogicDTO) {

    }
}
