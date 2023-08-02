package com.swms.wms.api.basic.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.Getter;

@Getter
@Dictionary
public enum WarehouseAreaTypeEnum {

    RECEIVE_AREA,

    STORAGE_AREA,

    STORAGE_CACHE,

    ABNORMAL_AREA,
}
