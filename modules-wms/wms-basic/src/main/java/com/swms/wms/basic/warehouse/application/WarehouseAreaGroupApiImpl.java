package com.swms.wms.basic.warehouse.application;

import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.BasicErrorDescEnum;
import com.swms.wms.api.basic.IWarehouseAreaGroupApi;
import com.swms.wms.api.basic.dto.WarehouseAreaGroupDTO;
import com.swms.wms.basic.warehouse.domain.entity.Location;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseAreaGroup;
import com.swms.wms.basic.warehouse.domain.repository.LocationRepository;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseAreaGroupRepository;
import com.swms.wms.basic.warehouse.domain.service.WarehouseService;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseAreaGroupTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class WarehouseAreaGroupApiImpl implements IWarehouseAreaGroupApi {

    @Autowired
    private WarehouseAreaGroupRepository warehouseAreaGroupRepository;

    @Autowired
    private WarehouseAreaGroupTransfer warehouseAreaGroupTransfer;

    @Autowired
    private WarehouseService warehouseAreaGroupService;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void save(WarehouseAreaGroupDTO warehouseAreaGroupDTO) {
        warehouseAreaGroupRepository.save(warehouseAreaGroupTransfer.toDO(warehouseAreaGroupDTO));
    }

    @Override
    public void update(WarehouseAreaGroupDTO warehouseAreaGroupDTO) {
        warehouseAreaGroupRepository.save(warehouseAreaGroupTransfer.toDO(warehouseAreaGroupDTO));
    }

    @Override
    @Transactional
    public void enable(Long id) {
        WarehouseAreaGroup warehouseAreaGroup = warehouseAreaGroupRepository.getById(id);

        List<Location> locations = warehouseAreaGroupService.getLocationsByWarehouseAreaGroup(warehouseAreaGroup);
        locations.forEach(Location::enable);
        locationRepository.saveAll(locations);

        warehouseAreaGroup.enable();
        warehouseAreaGroupRepository.save(warehouseAreaGroup);
    }

    @Override
    @Transactional
    public void disable(Long id) {
        WarehouseAreaGroup warehouseAreaGroup = warehouseAreaGroupRepository.getById(id);

        List<Location> locations = warehouseAreaGroupService.getLocationsByWarehouseAreaGroup(warehouseAreaGroup);
        locations.forEach(Location::disable);
        locationRepository.saveAll(locations);

        warehouseAreaGroup.disable();
        warehouseAreaGroupRepository.save(warehouseAreaGroup);
    }

    @Override
    public void delete(Long id) {
        WarehouseAreaGroup warehouseAreaGroup = warehouseAreaGroupRepository.getById(id);
        boolean validateResult = warehouseAreaGroupService.validate(warehouseAreaGroup);
        if (validateResult) {
            throw WmsException.throwWmsException(BasicErrorDescEnum.LOCATION_CONTAINS_STOCK);
        }

        warehouseAreaGroupRepository.delete(warehouseAreaGroup);
    }
}
