package com.swms.mdm.main.data.application;

import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.mdm.main.data.domain.repository.WarehouseMainDataRepository;
import com.swms.mdm.main.data.domain.transfer.WarehouseMainDataTransfer;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@DubboService
public class WarehouseMainDataApiImpl implements IWarehouseMainDataApi {

    @Autowired
    private WarehouseMainDataRepository warehouseRepository;

    @Autowired
    private WarehouseMainDataTransfer warehouseTransfer;

    @Override
    public void createWarehouse(WarehouseMainDataDTO warehouseDTO) {
        warehouseRepository.save(warehouseTransfer.toWarehouseMainData(warehouseDTO));
    }

    @Override
    public void updateWarehouse(WarehouseMainDataDTO warehouseDTO) {
        warehouseRepository.save(warehouseTransfer.toWarehouseMainData(warehouseDTO));
    }

    @Override
    public WarehouseMainDataDTO getWarehouse(String warehouseCode) {
        return warehouseTransfer.toDTO(warehouseRepository.getWarehouse(warehouseCode));
    }
}
