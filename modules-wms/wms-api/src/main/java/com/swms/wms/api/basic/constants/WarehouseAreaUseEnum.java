package com.swms.wms.api.basic.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.Getter;

@Getter
@Dictionary
public enum WarehouseAreaUseEnum {
    RECEIVE,
    PUT_AWAY_HOLDER,
    PICK,
}
