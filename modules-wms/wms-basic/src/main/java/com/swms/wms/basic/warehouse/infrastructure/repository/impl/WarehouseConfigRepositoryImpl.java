package com.swms.wms.basic.warehouse.infrastructure.repository.impl;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseConfig;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseConfigRepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.mapper.WarehouseConfigPORepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.transfer.WarehouseConfigPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseConfigRepositoryImpl implements WarehouseConfigRepository {

    @Autowired
    private WarehouseConfigPORepository warehouseConfigPORepository;

    @Autowired
    private WarehouseConfigPOTransfer warehouseConfigPOTransfer;

    @Override
    public void save(WarehouseConfig warehouseConfig) {
        warehouseConfigPORepository.save(warehouseConfigPOTransfer.toWarehouseConfigPO(warehouseConfig));
    }

    @Override
    public WarehouseConfig findByWarehouseCode(String warehouseCode) {
        return warehouseConfigPOTransfer.toWarehouseConfig(warehouseConfigPORepository.findByWarehouseCode(warehouseCode));
    }
}
