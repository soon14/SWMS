package com.swms.utils.validate;

@ValidObject(groups = ValidationSequence.ThirdGroup.class)
public interface IValidate {
    boolean validate();
}
