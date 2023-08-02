package com.swms.wms.api.basic.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Dictionary
public enum WorkStationOperationTypeEnum {

    ACCEPT("验收"),
    PICKING("拣选"),
    STOCK_TAKE("盘点"),
    ONE_STEP_INVENTORY_RELOCATION("一步式理库"),
    TWO_STEP_INVENTORY_RELOCATION("两步式理库"),
    ;
    private final String descCN;


}
