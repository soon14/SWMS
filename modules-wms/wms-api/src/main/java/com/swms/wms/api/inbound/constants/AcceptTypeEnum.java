package com.swms.wms.api.inbound.constants;

import com.swms.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcceptTypeEnum implements IEnum {

    DIRECT("direct", "direct"),
    RECEIVE("receive", "receive"),
    IN_WAREHOUSE("in_warehouse", "in_warehouse"),
    WAYBILL("waybill", "waybill"),
    ;

    private String value;
    private String label;

}
