package com.swms.wms.api.inbound.constants;

import lombok.Getter;

@Getter
public enum InboundPlanOrderStatusEnum {

    NEW,

    RECEIVING,

    RECEIVED,

    ACCEPTING,

    ACCEPTED,

    PUTTING_AWAY,

    PUT_AWAY,

    CANCEL;
}
