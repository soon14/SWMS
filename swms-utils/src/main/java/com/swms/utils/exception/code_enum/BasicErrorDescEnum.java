package com.swms.utils.exception.code_enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BasicErrorDescEnum implements IBaseError {

    BASIC_ERROR_DESC_ENUM("BAS001001", "basic base error"),

    // container error
    CONTAINER_NOT_EXIST("BAS001002", "container not exist"),
    CONTAINER_SPECIFIC_NOT_EXIST("BAS001003", "container specific not exist"),
    CONTAINER_SPECIFIC_CANNOT_CHANGE("BAS001004", "container specific cannot change, maybe it's already in use"),
    CONTAINER_SPECIFIC_SLOT_CODE_REPEAT("BAS001005", "container specific slot code repeat"),
    CONTAINER_SPECIFIC_SLOT_LEVEL_BAY_REPEAT("BAS001006", "container specific slot loc level bay repeat"),
    ;

    private final String code;

    private final String desc;

}
