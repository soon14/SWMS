package com.swms.utils.exception.code_enum;

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

    SYSTEM_EXEC_ERROR("CM010001", "system error."),
    DATABASE_UNIQUE_ERROR("CM010002", "database unique key repeated."),
    JSON_PARSER_ERROR("CM010003", "json parse error."),
    REPEAT_REQUEST("CM010004", "repeat request, please try again later."),

    ;


    private final String code;

    private final String desc;

}
