package com.swms.wms.outbound.domain.validator.impl;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.wms.outbound.domain.validator.IValidator;
import com.swms.wms.outbound.infrastructure.remote.OwnerMainDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OwnerValidator implements IValidator<List<String>, Void> {

    @Autowired
    protected OwnerMainDataFacade ownerMainDataApi;

    @Override
    public Void validate(List<String> ownerCodes) {
        final Set<String> dataOwnerCodes = ownerMainDataApi.getOwners(ownerCodes)
            .stream()
            .map(OwnerMainDataDTO::getOwnerCode)
            .collect(Collectors.toSet());

        if (ownerCodes.stream().distinct().count() != dataOwnerCodes.size()) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.OWNER_CODE_NOT_EXIST, ownerCodes);
        }
        return null;
    }

    @Override
    public ValidatorName getValidatorName() {
        return ValidatorName.OWNER;
    }
}
