package com.swms.wms.api.basic.constants;

import com.swms.common.utils.dictionary.Dictionary;
import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Dictionary
public enum LocationStatusEnum implements IEnum {

    PUT_AWAY_ONLY("PUT_AWAY_ONLY", "仅上架"),
    TAKE_OFF_ONLY("TAKE_OFF_ONLY", "仅下架"),
    PUT_AWAY_PUT_DOWN("PUT_AWAY_PUT_DOWN", "上架&下架"),
    NONE("NONE", "禁用"),
    ;

    private String value;
    private String label;

}
