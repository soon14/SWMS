package com.swms.wms.basic.warehouse.infrastructure.repository.impl;

import com.swms.wms.basic.warehouse.domain.entity.WarehouseLogic;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseLogicRepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.mapper.WarehouseLogicPORepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.transfer.WarehouseLogicPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseLogicRepositoryImpl implements WarehouseLogicRepository {

    @Autowired
    private WarehouseLogicPORepository warehouseLogicPORepository;

    @Autowired
    private WarehouseLogicPOTransfer warehouseLogicPOTransfer;

    @Override
    public void save(WarehouseLogic warehouseLogic) {
        warehouseLogicPORepository.save(warehouseLogicPOTransfer.toPO(warehouseLogic));
    }

    @Override
    public WarehouseLogic getById(Long id) {
        return warehouseLogicPOTransfer.toDO(warehouseLogicPORepository.findById(id).orElseThrow());
    }

    @Override
    public List<WarehouseLogic> getByWarehouseAreaIds(List<Long> warehouseAreaIds) {
        return warehouseLogicPOTransfer.toDOS(warehouseLogicPORepository.findByWarehouseAreaIdIn(warehouseAreaIds));
    }

    @Override
    public void saveAll(List<WarehouseLogic> warehouseLogics) {
        warehouseLogicPORepository.saveAll(warehouseLogicPOTransfer.toPOS(warehouseLogics));
    }
}
