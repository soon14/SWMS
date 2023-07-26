package com.swms.wms.basic.warehouse.application;

import com.google.common.collect.Lists;
import com.swms.wms.api.basic.ILocationApi;
import com.swms.wms.api.basic.dto.AisleDTO;
import com.swms.wms.api.basic.dto.LocationDTO;
import com.swms.wms.api.basic.dto.LocationUpdateDTO;
import com.swms.wms.basic.warehouse.domain.entity.Location;
import com.swms.wms.basic.warehouse.domain.repository.LocationRepository;
import com.swms.wms.basic.warehouse.domain.transfer.LocationTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class LocationApiImpl implements ILocationApi {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTransfer locationTransfer;

    @Override
    public void createLocations(List<AisleDTO> aisleDTOS, List<LocationDTO> locationDTOS) {
        locationRepository.createLocations(locationTransfer.toAisles(aisleDTOS), locationTransfer.toLocations(locationDTOS));
    }

    @Override
    public void update(LocationUpdateDTO locationUpdateDTO) {
        List<Location> locations = locationRepository.getAllByIds(locationUpdateDTO.getLocationIds());

        locations.forEach(v -> {
            v.setHeat(locationUpdateDTO.getHeat());
            v.setWarehouseLogicId(locationUpdateDTO.getWarehouseLogicId());
            v.setLocationStatus(locationUpdateDTO.getLocationStatus());
        });

        locationRepository.saveAll(locations);
    }

    @Override
    public void delete(Long id) {
        Location location = locationRepository.getById(id);
        location.delete();
        locationRepository.saveAll(Lists.newArrayList(location));
    }

    @Override
    public List<LocationDTO> getByAisle(String aisleCode, Long warehouseAreaId) {
        return locationTransfer.toLocationDTOS(locationRepository.getByAisle(aisleCode, warehouseAreaId));
    }

    @Override
    public void deleteByAisle(String aisleCode, Long warehouseAreaId) {
        List<Location> locations = locationRepository.getByAisle(aisleCode, warehouseAreaId);
        locations.forEach(Location::delete);
        locationRepository.saveAll(locations);
    }
}
