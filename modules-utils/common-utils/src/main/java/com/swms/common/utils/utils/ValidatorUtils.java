package com.swms.common.utils.utils;

import com.swms.common.utils.exception.CommonException;
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
            throw new CommonException(violation.getPropertyPath().toString() +
                "[" + violation.getInvalidValue() + "] " + violation.getMessage());
        }
    }
}
