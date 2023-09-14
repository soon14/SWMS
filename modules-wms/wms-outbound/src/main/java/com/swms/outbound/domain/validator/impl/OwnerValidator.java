package com.swms.outbound.domain.validator.impl;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum;
import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.outbound.domain.validator.IValidator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OwnerValidator implements IValidator<List<String>, Void> {

    @DubboReference
    protected IOwnerMainDataApi iOwnerApi;

    @Override
    public Void validate(List<String> ownerCodes) {
        final Set<String> dataOwnerCodes = iOwnerApi.getOwners(ownerCodes)
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
