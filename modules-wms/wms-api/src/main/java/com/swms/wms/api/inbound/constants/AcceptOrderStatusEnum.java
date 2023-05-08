package com.swms.wms.api.inbound.constants;

import lombok.Getter;

@Getter
public enum AcceptOrderStatusEnum {

    NEW,

    ACCEPTING,

    ACCEPTED,

    PUTTING_AWAY,

    PUT_AWAY;
}
