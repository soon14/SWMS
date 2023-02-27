package com.swms.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkStationOperationTypeEnum {


    INBOUND("入库"),
    RECEIVE("收货"),
    DIVIDE("退货分理"),
    RANDOM_REPLENISH("随机上架"),
    REPLENISH("推荐容器上架"),
    ARTIFICIAL_REPLENISH("选择容器上架"),

    EMPTY_CONTAINER_OUTBOUND("空箱出库"),
    OUTBOUND("出库"),
    CHOICE_CONTAINER_OUTBOUND("选择容器出库"),

    STOCK_TAKE("盘点"),

    ONE_STEP_INVENTORY_RELOCATION("一步式理库"),
    TWO_STEP_INVENTORY_RELOCATION("两步式理库"),


    MAINTAIN("养护");

    private final String descCN;


}
