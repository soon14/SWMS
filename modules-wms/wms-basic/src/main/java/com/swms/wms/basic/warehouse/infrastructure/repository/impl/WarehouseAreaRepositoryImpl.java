package com.swms.wms.basic.warehouse.infrastructure.repository.impl;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseAreaRepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.mapper.WarehouseAreaPORepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.transfer.WarehouseAreaPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseAreaRepositoryImpl implements WarehouseAreaRepository {

    @Autowired
    private WarehouseAreaPORepository warehouseAreaPORepository;

    @Autowired
    private WarehouseAreaPOTransfer warehouseAreaPOTransfer;

    @Override
    public void save(WarehouseArea warehouseArea) {
        warehouseAreaPORepository.save(warehouseAreaPOTransfer.toPO(warehouseArea));
    }
}
