package com.swms.mdm.main.data.infrastructure.repository.impl;

import com.swms.mdm.main.data.domain.entity.WarehouseMainData;
import com.swms.mdm.main.data.domain.repository.WarehouseMainDataRepository;
import com.swms.mdm.main.data.infrastructure.persistence.mapper.WarehouseMainDataPORepository;
import com.swms.mdm.main.data.infrastructure.persistence.transfer.WarehouseMainDataPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseMainDataRepositoryImpl implements WarehouseMainDataRepository {

    @Autowired
    private WarehouseMainDataPORepository warehouseMainDataPORepository;

    @Autowired
    private WarehouseMainDataPOTransfer warehouseMainDataPOTransfer;

    @Override
    public void save(WarehouseMainData warehouse) {
        warehouseMainDataPORepository.save(warehouseMainDataPOTransfer.toPO(warehouse));
    }

    @Override
    public WarehouseMainData findById(Long id) {
        return warehouseMainDataPOTransfer.toDO(warehouseMainDataPORepository.findById(id).orElseThrow());
    }

    @Override
    public WarehouseMainData getWarehouse(String warehouseCode) {
        return warehouseMainDataPOTransfer.toDO(warehouseMainDataPORepository.findByWarehouseCode(warehouseCode));
    }
}
