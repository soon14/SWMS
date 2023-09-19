package com.swms.outbound.domain.validator.impl;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.outbound.domain.validator.IValidator;
import com.swms.outbound.facade.WarehouseMainDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WarehouseValidator implements IValidator<List<String>, Void> {

    @Autowired
    protected WarehouseMainDataFacade warehouseMainDataApi;

    @Override
    public Void validate(List<String> warehouseCodes) {
        final Set<String> dataWarehouseCodes = warehouseMainDataApi.getWarehouses(warehouseCodes)
            .stream()
            .map(WarehouseMainDataDTO::getWarehouseCode)
            .collect(Collectors.toSet());

        if (warehouseCodes.stream().distinct().count() != dataWarehouseCodes.size()) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.WAREHOUSE_CODE_NOT_EXIST, warehouseCodes);
        }
        return null;
    }

    @Override
    public ValidatorName getValidatorName() {
        return ValidatorName.WAREHOUSE;
    }
}
