package com.swms.wms.basic.warehouse.domain.service.impl;

import com.swms.wms.basic.warehouse.domain.entity.Location;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseAreaGroup;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseLogic;
import com.swms.wms.basic.warehouse.domain.repository.LocationRepository;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseAreaRepository;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseLogicRepository;
import com.swms.wms.basic.warehouse.domain.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseAreaRepository warehouseAreaRepository;

    @Autowired
    private WarehouseLogicRepository warehouseLogicRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public boolean validate(WarehouseAreaGroup warehouseAreaGroup) {

        List<WarehouseArea> warehouseAreas = warehouseAreaRepository
            .getByWarehouseAreaGroup(warehouseAreaGroup.getWarehouseAreaGroupCode(), warehouseAreaGroup.getWarehouseCode());

        return warehouseAreas.stream().anyMatch(warehouseArea -> {
            List<Location> locations = locationRepository.getByWarehouseAreaId(warehouseArea.getId());
            return locations.stream().anyMatch(Location::isOccupied);
        });
    }

    @Override
    public List<Location> getLocationsByWarehouseAreaGroup(WarehouseAreaGroup warehouseAreaGroup) {
        List<WarehouseArea> warehouseAreas = warehouseAreaRepository
            .getByWarehouseAreaGroup(warehouseAreaGroup.getWarehouseAreaGroupCode(), warehouseAreaGroup.getWarehouseCode());
        return warehouseAreas.stream().flatMap(warehouseArea ->
            locationRepository.getByWarehouseAreaId(warehouseArea.getId()).stream()).toList();
    }

    @Override
    public List<Location> getLocationsByWarehouseArea(WarehouseArea warehouseArea) {
        return locationRepository.getByWarehouseAreaId(warehouseArea.getId());
    }

    @Override
    public List<Location> getLocationsByWarehouseLogic(WarehouseLogic warehouseLogic) {
        return locationRepository.getByWarehouseLogicId(warehouseLogic.getId());
    }

    @Override
    public List<WarehouseArea> getWarehouseAreasByWarehouseAreaGroup(WarehouseAreaGroup warehouseAreaGroup) {
        return warehouseAreaRepository
            .getByWarehouseAreaGroup(warehouseAreaGroup.getWarehouseAreaGroupCode(), warehouseAreaGroup.getWarehouseCode());
    }

    @Override
    public List<WarehouseLogic> getWarehouseLogicsByWarehouseAreaIds(List<Long> warehouseAreaIds) {
        return warehouseLogicRepository.getByWarehouseAreaIds(warehouseAreaIds);
    }
}
