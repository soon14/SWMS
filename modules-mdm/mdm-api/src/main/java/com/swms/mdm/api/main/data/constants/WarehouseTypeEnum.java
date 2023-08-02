package com.swms.mdm.api.main.data.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.Getter;

@Getter
@Dictionary
public enum WarehouseTypeEnum {
    CENTER_WAREHOUSE,
    DELIVERY_WAREHOUSE,
    TRANSIT_WAREHOUSE,
    HUB_WAREHOUSE,
    FACTORY_WAREHOUSE,
    RETURN_WAREHOUSE,
    FRONT_WAREHOUSE,
}
