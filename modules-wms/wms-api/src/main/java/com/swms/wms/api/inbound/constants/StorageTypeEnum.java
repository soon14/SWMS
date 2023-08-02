package com.swms.wms.api.inbound.constants;

import com.swms.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StorageTypeEnum implements IEnum {

    STORAGE("storage", "存储"),
    OVERSTOCK("overstock", "越库"),
    IN_TRANSIT("in_transit", "在途"),
    ;

    private String value;
    private String label;
}
