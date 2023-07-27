package com.swms.wms.basic.warehouse.application;

import com.google.common.collect.Lists;
import com.swms.wms.api.basic.IWarehouseAreaApi;
import com.swms.wms.api.basic.dto.WarehouseAreaDTO;
import com.swms.wms.basic.warehouse.domain.entity.Location;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseLogic;
import com.swms.wms.basic.warehouse.domain.repository.LocationRepository;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseAreaRepository;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseLogicRepository;
import com.swms.wms.basic.warehouse.domain.service.WarehouseService;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseAreaTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class WarehouseAreaApiImpl implements IWarehouseAreaApi {

    @Autowired
    private WarehouseAreaRepository warehouseAreaRepository;

    @Autowired
    private WarehouseLogicRepository warehouseLogicRepository;

    @Autowired
    private WarehouseAreaTransfer warehouseAreaTransfer;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void save(WarehouseAreaDTO warehouseAreaDTO) {
        warehouseAreaRepository.save(warehouseAreaTransfer.toDO(warehouseAreaDTO));
    }

    @Override
    public void update(WarehouseAreaDTO warehouseAreaDTO) {
        warehouseAreaRepository.save(warehouseAreaTransfer.toDO(warehouseAreaDTO));
    }

    @Override
    @Transactional
    public void enable(Long id) {
        WarehouseArea warehouseArea = warehouseAreaRepository.getById(id);
        List<Location> locations = warehouseService.getLocationsByWarehouseArea(warehouseArea);

        locations.forEach(Location::enable);
        locationRepository.saveAll(locations);

        warehouseArea.enable();
        warehouseAreaRepository.save(warehouseArea);
    }

    @Override
    @Transactional
    public void disable(Long id) {
        WarehouseArea warehouseArea = warehouseAreaRepository.getById(id);
        List<Location> locations = warehouseService.getLocationsByWarehouseArea(warehouseArea);

        locations.forEach(Location::disable);
        locationRepository.saveAll(locations);

        warehouseArea.disable();
        warehouseAreaRepository.save(warehouseArea);
    }

    @Override
    public void delete(Long id) {

        WarehouseArea warehouseArea = warehouseAreaRepository.getById(id);
        List<WarehouseLogic> warehouseLogics = warehouseService
            .getWarehouseLogicsByWarehouseAreaIds(Lists.newArrayList(warehouseArea.getId()));

        warehouseLogics.forEach(WarehouseLogic::delete);
        warehouseLogicRepository.saveAll(warehouseLogics);

        warehouseArea.delete();
        warehouseAreaRepository.save(warehouseArea);
    }

    @Override
    public WarehouseAreaDTO getById(Long warehouseAreaId) {
        return warehouseAreaTransfer.toDTO(warehouseAreaRepository.getById(warehouseAreaId));
    }
}
