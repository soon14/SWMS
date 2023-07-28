package com.swms.wms.basic.warehouse.infrastructure.repository.impl;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseAreaGroup;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseAreaGroupRepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.mapper.WarehouseAreaGroupPORepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.transfer.WarehouseAreaGroupPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseAreaGroupRepositoryImpl implements WarehouseAreaGroupRepository {

    @Autowired
    private WarehouseAreaGroupPORepository warehouseAreaGroupPORepository;

    @Autowired
    private WarehouseAreaGroupPOTransfer warehouseAreaGroupPOTransfer;

    @Override
    public void save(WarehouseAreaGroup warehouseAreaGroup) {
        warehouseAreaGroupPORepository.save(warehouseAreaGroupPOTransfer.toPO(warehouseAreaGroup));
    }

    @Override
    public WarehouseAreaGroup getById(Long id) {
        return warehouseAreaGroupPOTransfer.toDO(warehouseAreaGroupPORepository.findById(id).orElseThrow());
    }

}
