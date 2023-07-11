package com.swms.wms.basic.warehouse.application;

import com.swms.wms.api.basic.IWarehouseConfigApi;
import com.swms.wms.api.basic.dto.WarehouseConfigDTO;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseConfig;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseConfigRepository;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseConfigTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class WarehouseConfigApplicationImpl implements IWarehouseConfigApi {

    @Autowired
    private WarehouseConfigRepository warehouseConfigRepository;

    @Autowired
    private WarehouseConfigTransfer warehouseConfigTransfer;

    @Override
    @Transactional
    public void save(WarehouseConfigDTO warehouseConfigDTO) {
        warehouseConfigRepository.save(warehouseConfigTransfer.toWarehouseConfig(warehouseConfigDTO));
    }

    @Override
    public void update(WarehouseConfigDTO warehouseConfigDTO) {
        warehouseConfigRepository.save(warehouseConfigTransfer.toWarehouseConfig(warehouseConfigDTO));
    }

    @Override
    public WarehouseConfigDTO getWarehouseConfig(String warehouseCode) {
        WarehouseConfig warehouseConfig = warehouseConfigRepository.findByWarehouseCode(warehouseCode);
        return warehouseConfigTransfer.toWarehouseConfigDTO(warehouseConfig);
    }
}
