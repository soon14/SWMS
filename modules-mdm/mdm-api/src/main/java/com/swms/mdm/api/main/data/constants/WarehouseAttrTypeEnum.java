package com.swms.mdm.api.main.data.constants;

import com.swms.utils.dictionary.Dictionary;
import lombok.Getter;

@Getter
@Dictionary
public enum WarehouseAttrTypeEnum {
    NORMAL,
    COLD_CHAIN,
    DANGEROUS,
    BONDED,
}
