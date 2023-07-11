package com.swms.wms.basic.warehouse.application;

import com.swms.wms.api.basic.IWarehouseAreaGroupApi;
import com.swms.wms.api.basic.dto.WarehouseAreaGroupDTO;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseAreaGroupRepository;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseAreaGroupTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class WarehouseAreaGroupApiImpl implements IWarehouseAreaGroupApi {

    @Autowired
    private WarehouseAreaGroupRepository warehouseAreaGroupRepository;

    @Autowired
    private WarehouseAreaGroupTransfer warehouseAreaGroupTransfer;

    @Override
    public void save(WarehouseAreaGroupDTO warehouseAreaGroupDTO) {
        warehouseAreaGroupRepository.save(warehouseAreaGroupTransfer.toDO(warehouseAreaGroupDTO));
    }

    @Override
    public void update(WarehouseAreaGroupDTO warehouseAreaGroupDTO) {
        warehouseAreaGroupRepository.save(warehouseAreaGroupTransfer.toDO(warehouseAreaGroupDTO));
    }

    @Override
    public void enable(Long id) {

    }

    @Override
    public void disable(Long id) {

    }

    @Override
    public void delete(WarehouseAreaGroupDTO WarehouseAreaGroupDTO) {

    }
}
