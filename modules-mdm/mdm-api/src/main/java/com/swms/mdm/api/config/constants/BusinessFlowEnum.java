package com.swms.mdm.api.config.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.Getter;

@Getter
@Dictionary
public enum BusinessFlowEnum {

    INBOUND,
    OUTBOUND,
    STOCK_TAKE,
}
