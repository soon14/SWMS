package com.swms.wms.basic.warehouse.infrastructure.repository.impl;

import com.swms.wms.basic.warehouse.domain.entity.Aisle;
import com.swms.wms.basic.warehouse.domain.entity.Location;
import com.swms.wms.basic.warehouse.domain.repository.LocationRepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.mapper.AislePORepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.mapper.LocationPORepository;
import com.swms.wms.basic.warehouse.infrastructure.persistence.transfer.LocationPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationRepositoryImpl implements LocationRepository {

    @Autowired
    private LocationPORepository locationRepository;

    @Autowired
    private AislePORepository aislePORepository;

    @Autowired
    private LocationPOTransfer locationPOTransfer;

    @Override
    public List<Location> getAllByIds(List<Long> locationIds) {
        return locationPOTransfer.toLocationDOS(locationRepository.findAllById(locationIds));
    }

    @Override
    @Transactional
    public void createLocations(List<Aisle> aisles, List<Location> locations) {
        aislePORepository.saveAll(locationPOTransfer.toAislePOS(aisles));
        locationRepository.saveAll(locationPOTransfer.toLocationPOS(locations));
    }

    @Override
    @Transactional
    public void saveAll(List<Location> locations) {
        locationRepository.saveAll(locationPOTransfer.toLocationPOS(locations));
    }

    @Override
    public Location getById(Long id) {
        return locationPOTransfer.toLocationDO(locationRepository.findById(id).orElseThrow());
    }

    @Override
    public List<Location> getByAisle(String aisleCode, Long warehouseAreaId) {
        return locationPOTransfer.toLocationDOS(locationRepository.findByAisleCodeAndWarehouseAreaId(aisleCode, warehouseAreaId));
    }
}
