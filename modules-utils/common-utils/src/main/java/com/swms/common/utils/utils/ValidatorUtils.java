package com.swms.common.utils.utils;

import static com.swms.common.utils.exception.code_enum.CommonErrorDescEnum.PARAMETER_ERROR;

import com.swms.common.utils.exception.WmsException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class ValidatorUtils {

    private ValidatorUtils() {

    }

    private static Validator validator;

    @Autowired
    public ValidatorUtils(Validator validator) {
        ValidatorUtils.validator = validator;
    }

    public static <T> void validate(Collection<T> object) {
        object.forEach(ValidatorUtils::validate);
    }

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (CollectionUtils.isNotEmpty(violations)) {
            ConstraintViolation<Object> violation = violations.iterator().next();
            throw WmsException.builder().code(PARAMETER_ERROR.getCode())
                .message(violation.getPropertyPath().toString() +
                    "[" + violation.getInvalidValue() + "] " + violation.getMessage()).build();
        }
    }
}
