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

    INBOUND_STATUS_ERROR("IN001002", "operation failed cause inbound status error"),

    INBOUND_OVER_ACCEPT_ERROR("IN001003", "inbound sku {0} over accept"),

    INBOUND_BOX_NO_ERROR("IN001004", "inbound box no {0} error , cann't find.");


    private final String code;

    private final String desc;

}
