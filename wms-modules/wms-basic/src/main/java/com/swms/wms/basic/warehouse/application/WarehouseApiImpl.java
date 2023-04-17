package com.swms.wms.basic.warehouse.application;

import com.swms.wms.api.basic.IWarehouseApi;
import com.swms.wms.api.basic.dto.WarehouseDTO;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseRepository;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseApiImpl implements IWarehouseApi {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseTransfer warehouseTransfer;

    @Override
    public void createWarehouse(WarehouseDTO warehouseDTO) {

    }

    @Override
    public void updateWarehouse(WarehouseDTO warehouseDTO) {

    }

    @Override
    public void enableWarehouse(Long id) {

    }

    @Override
    public void disableWarehouse(Long id) {

    }

    @Override
    public void deleteWarehouse(WarehouseDTO warehouseDTO) {

    }

    @Override
    public WarehouseDTO getWarehouse(String warehouseCode) {
        return warehouseTransfer.toWarehouseDTO(warehouseRepository.findByWarehouseCode(warehouseCode));
    }
}
