package com.swms.wms.basic.warehouse.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swms.wms.basic.warehouse.domain.entity.Warehouse;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseRepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.mapper.WarehouseMapper;
import com.swms.wms.basic.warehouse.infrastructure.persistence.po.WarehousePO;
import com.swms.wms.basic.warehouse.infrastructure.persistence.transfer.WarehousePOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseRepositoryImpl implements WarehouseRepository {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private WarehousePOTransfer warehousePOTransfer;

    @Override
    public Warehouse findByWarehouseCode(String warehouseCode) {
        LambdaQueryWrapper<WarehousePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehousePO::getWarehouseCode, warehouseCode);
        return warehousePOTransfer.toWarehouse(warehouseMapper.selectOne(wrapper));
    }
}
