package com.swms.common.utils.exception.code_enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * InboundErrorDescEnum
 *
 * @author sws
 * @date 2023/02/24
 */
@Getter
@AllArgsConstructor
public enum InboundErrorDescEnum implements IBaseError {

    INBOUND_CST_ORDER_NO_REPEATED("IN001001", "inbound customer order no {0} repeated"),

    INBOUND_STATUS_ERROR("IN001002", "operation failed cause inbound status error");


    private final String code;

    private final String desc;

}
