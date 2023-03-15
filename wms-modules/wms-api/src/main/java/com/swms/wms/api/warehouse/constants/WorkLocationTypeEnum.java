package com.swms.wms.api.warehouse.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkLocationTypeEnum {

    BUFFER_SHELVING("缓存货架"),

    ROBOT("机器人"),

    CONVEYOR("输送线"),
    ;

    private final String descCN;
}
