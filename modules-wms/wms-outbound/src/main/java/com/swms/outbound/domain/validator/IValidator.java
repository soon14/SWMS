package com.swms.outbound.domain.validator;

public interface IValidator<T, R> {

    R validate(T validateObject);

    ValidatorName getValidatorName();

    enum ValidatorName {
        WAREHOUSE, OWNER, SKU;
    }

}
