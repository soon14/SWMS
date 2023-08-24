package com.swms.mdm.main.data.infrastructure.repository.impl;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum;
import com.swms.mdm.main.data.domain.entity.WarehouseMainData;
import com.swms.mdm.main.data.domain.repository.WarehouseMainDataRepository;
import com.swms.mdm.main.data.infrastructure.persistence.mapper.WarehouseMainDataPORepository;
import com.swms.mdm.main.data.infrastructure.persistence.transfer.WarehouseMainDataPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseMainDataRepositoryImpl implements WarehouseMainDataRepository {

    @Autowired
    private WarehouseMainDataPORepository warehouseMainDataPORepository;

    @Autowired
    private WarehouseMainDataPOTransfer warehouseMainDataPOTransfer;

    @Override
    public void save(WarehouseMainData warehouse) {
        Optional.ofNullable(warehouse)
            .map(warehouseMainDataPOTransfer::toPO)
            .ifPresent(warehouseMainDataPORepository::save);
    }

    @Override
    public WarehouseMainData findById(Long id) {
        return warehouseMainDataPORepository.findById(id)
            .map(warehouseMainDataPOTransfer::toDO)
            .orElseThrow(WmsException.throwWmsExceptionSup(MainDataErrorDescEnum.WAREHOUSE_EXIST));
    }

    @Override
    public WarehouseMainData getWarehouse(String warehouseCode) {
        return warehouseMainDataPORepository.findByWarehouseCode(warehouseCode)
            .map(warehouseMainDataPOTransfer::toDO)
            .orElseThrow(WmsException.throwWmsExceptionSup(MainDataErrorDescEnum.WAREHOUSE_CODE_NOT_EXIST, warehouseCode));
    }

    @Override
    public Collection<WarehouseMainData> getWarehouses(Collection<String> warehouseCodes) {
        return warehouseMainDataPORepository.findByWarehouseCodeIn(warehouseCodes)
            .stream()
            .flatMap(Collection::stream)
            .map(warehouseMainDataPOTransfer::toDO)
            .collect(Collectors.toSet());
    }
}
