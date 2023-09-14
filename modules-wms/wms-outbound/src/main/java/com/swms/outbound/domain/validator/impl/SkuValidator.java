package com.swms.outbound.domain.validator.impl;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.outbound.domain.validator.IValidator;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkuValidator implements IValidator<SkuValidator.SkuValidatorObject, Set<SkuMainDataDTO>> {

    @DubboReference
    protected ISkuMainDataApi iSkuApi;

    @Override
    public Set<SkuMainDataDTO> validate(SkuValidatorObject skuValidatorObject) {

        Set<SkuMainDataDTO> skuMainDataDTOS = iSkuApi.getSkuMainData(skuValidatorObject.getSkuCodes())
            .stream()
            .filter(v -> skuValidatorObject.getOwnerCode().equals(v.getOwnerCode()))
            .collect(Collectors.toSet());

        final Set<String> dataSkuCodes = skuMainDataDTOS.stream()
            .map(SkuMainDataDTO::getSkuCode)
            .collect(Collectors.toSet());

        if (dataSkuCodes.size() != skuMainDataDTOS.size()) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.SOME_SKU_CODE_NOT_EXIST, skuValidatorObject.getSkuCodes());
        }

        return skuMainDataDTOS;
    }

    @Override
    public ValidatorName getValidatorName() {
        return ValidatorName.SKU;
    }

    @Data
    @Accessors(chain = true)
    public static class SkuValidatorObject {
        private List<String> skuCodes;
        private String ownerCode;

        public SkuValidatorObject(List<String> skuCodes, String ownerCode) {
            this.skuCodes = skuCodes;
            this.ownerCode = ownerCode;
        }
    }


}
