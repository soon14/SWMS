package com.swms.outbound.domain.validator.impl;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum;
import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.outbound.domain.validator.IValidator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WarehouseValidator implements IValidator<List<String>, Void> {

    @DubboReference
    protected IWarehouseMainDataApi iWarehouseApi;

    @Override
    public Void validate(List<String> warehouseCodes) {
        final Set<String> dataWarehouseCodes = iWarehouseApi.getWarehouses(warehouseCodes)
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
