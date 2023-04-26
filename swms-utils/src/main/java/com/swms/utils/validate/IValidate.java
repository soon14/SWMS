package com.swms.utils.validate;

@ValidObject(groups = ValidationSequence.Extended.class)
public interface IValidate {
    boolean validate();
}
