package com.swms.common.exception.code_enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * InboundErrorDescEnum
 *
 * @author krystal-2023
 * @date 2023/02/24
 */
@Getter
@AllArgsConstructor
public enum CommonErrorDescEnum implements IBaseError {

    SYSTEM_EXEC_ERROR("CM010001", "database unique key repeated."),

    DATABASE_UNIQUE_ERROR("CM010002", "database unique key repeated."),


    ;


    private final String code;

    private final String desc;

}
