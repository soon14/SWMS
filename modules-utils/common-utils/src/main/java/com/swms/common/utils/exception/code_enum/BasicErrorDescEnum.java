package com.swms.common.utils.exception.code_enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BasicErrorDescEnum implements IBaseError {

    BASIC_ERROR_DESC_ENUM("BA001001", "basic base error"),

    // container error
    CONTAINER_NOT_EXIST("BA001002", "container not exist"),
    CONTAINER_SPECIFIC_NOT_EXIST("BA001003", "container specific not exist"),
    CONTAINER_SPECIFIC_CANNOT_CHANGE("BA001004", "container specific cannot change, maybe it's already in use"),
    CONTAINER_SPECIFIC_SLOT_CODE_REPEAT("BA001005", "container specific slot code repeat"),
    CONTAINER_SPECIFIC_SLOT_LEVEL_BAY_REPEAT("BA001006", "container specific slot loc level bay repeat"),

    // location error
    LOCATION_CONTAINS_STOCK("BAS010001", "location contains stocks"),
    FORBIDDEN_OPERATE_MULTIPLE_AISLE("BAS010002", "forbidden operate multiple aisles"),

    //warehouse
    WAREHOUSE_LOGIC_CONTAINER_LOCATION("BAS020001", "warehouse logic {0} contains locations"),

    ;

    private final String code;

    private final String desc;

}
