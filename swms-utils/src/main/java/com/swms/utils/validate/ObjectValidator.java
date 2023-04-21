package com.swms.utils.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Order
@Slf4j
public class ObjectValidator implements ConstraintValidator<ValidObject, Object> {

    private static final String VALIDATE_METHOD = "validate";

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            Method method = value.getClass().getDeclaredMethod(VALIDATE_METHOD);
            Object invokeResult = method.invoke(value);
            if (invokeResult instanceof Boolean result) {
                return result;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("method validate not found or invoke error: ", e);
            return false;
        }
        return true;
    }
}
