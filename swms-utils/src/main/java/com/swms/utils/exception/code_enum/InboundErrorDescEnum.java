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
public enum InboundErrorDescEnum implements IBaseError {

    /**
     * 入库单基本错误
     */
    INBOUND_BASE_ERROR("IN001001", "inbound base error"),

    INBOUND_STATUS_ERROR("IN001002", "operation failed cause inbound status error");


    private final String code;

    private final String desc;

}
