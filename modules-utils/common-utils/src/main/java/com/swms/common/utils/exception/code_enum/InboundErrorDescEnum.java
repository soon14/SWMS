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

    // inbound plan order
    INBOUND_CST_ORDER_NO_REPEATED("IN001001", "inbound customer order no {0} repeated"),

    INBOUND_STATUS_ERROR("IN001002", "operation failed cause inbound status error"),

    INBOUND_OVER_ACCEPT_ERROR("IN001003", "inbound sku {0} over accept"),

    INBOUND_BOX_NO_ERROR("IN001004", "inbound box no {0} error , can not find"),

    INBOUND_BOX_NO_EXIST("IN001005", "inbound box no existing"),

    // receive order


    // accept order
    ACCEPT_ORDER_HAD_AUDIT("IN003001", "accept order {0} had audit"),


    ;


    private final String code;

    private final String desc;

}
