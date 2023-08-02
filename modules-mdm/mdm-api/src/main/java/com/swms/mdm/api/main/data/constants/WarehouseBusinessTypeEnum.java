package com.swms.mdm.api.main.data.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.Getter;

@Getter
@Dictionary
public enum WarehouseBusinessTypeEnum {
    TOB,
    TOC,
    RETURN,
    CROSS_BORDER,
    CONSUMABLES,
}
