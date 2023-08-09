package com.swms.mdm.api.config.constants;

import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParameterCodeEnum implements IEnum {

    INBOUND_OVER_ACCEPT("inbound_over_accept", "inbound_over_accept"),
    INBOUND_ALLOW_MULTIPLE_ARRIVALS("inbound_allowing_multiple_arrivals", "inbound_allowing_multiple_arrivals"),
    INBOUND_ALLOW_PRE_OCCUPYING_IN_TRANSIT("inbound_allow_pre_occupying_in_transit", "inbound_allow_pre_occupying_in_transit"),
    INBOUND_NEED_RECEIVE("inbound_need_receive", "inbound_need_receive"),
    ;

    private String value;
    private String label;
}
