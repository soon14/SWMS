package com.swms.wms.basic.warehouse.application;

import static com.swms.common.utils.exception.code_enum.BasicErrorDescEnum.WAREHOUSE_LOGIC_CONTAINER_LOCATION;

import com.swms.common.utils.exception.WmsException;
import com.swms.wms.api.basic.IWarehouseLogicApi;
import com.swms.wms.api.basic.dto.WarehouseLogicDTO;
import com.swms.wms.basic.warehouse.domain.entity.Location;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseLogic;
import com.swms.wms.basic.warehouse.domain.repository.LocationRepository;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseLogicRepository;
import com.swms.wms.basic.warehouse.domain.service.WarehouseService;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseLogicTransfer;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class WarehouseLogicApiImpl implements IWarehouseLogicApi {

    @Autowired
    private WarehouseLogicRepository warehouseLogicRepository;

    @Autowired
    private WarehouseLogicTransfer warehouseLogicTransfer;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void save(WarehouseLogicDTO warehouseLogicDTO) {
        warehouseLogicRepository.save(warehouseLogicTransfer.toDO(warehouseLogicDTO));
    }

    @Override
    public void update(WarehouseLogicDTO warehouseLogicDTO) {
        warehouseLogicRepository.save(warehouseLogicTransfer.toDO(warehouseLogicDTO));
    }

    @Override
    public void enable(Long id) {
        WarehouseLogic warehouseLogic = warehouseLogicRepository.getById(id);
        List<Location> locations = warehouseService.getLocationsByWarehouseLogic(warehouseLogic);

        locations.forEach(Location::enable);
        locationRepository.saveAll(locations);

        warehouseLogic.enable();
        warehouseLogicRepository.save(warehouseLogic);
    }

    @Override
    public void disable(Long id) {
        WarehouseLogic warehouseLogic = warehouseLogicRepository.getById(id);
        List<Location> locations = warehouseService.getLocationsByWarehouseLogic(warehouseLogic);

        locations.forEach(Location::disable);
        locationRepository.saveAll(locations);

        warehouseLogic.disable();
        warehouseLogicRepository.save(warehouseLogic);
    }

    @Override
    public void delete(Long id) {
        WarehouseLogic warehouseLogic = warehouseLogicRepository.getById(id);
        List<Location> locations = warehouseService.getLocationsByWarehouseLogic(warehouseLogic);

        if (CollectionUtils.isNotEmpty(locations)) {
            throw WmsException.throwWmsException(WAREHOUSE_LOGIC_CONTAINER_LOCATION, warehouseLogic.getWarehouseLogicCode());
        }

        warehouseLogic.delete();
        warehouseLogicRepository.save(warehouseLogic);
    }
}
