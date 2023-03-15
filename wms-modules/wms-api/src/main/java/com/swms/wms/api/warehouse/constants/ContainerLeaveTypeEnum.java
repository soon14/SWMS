package com.swms.wms.api.warehouse.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContainerLeaveTypeEnum {

    /**
     * 离开
     */
    LEAVE(0, "离开"),

    /**
     * 人工取出
     */
    MANUAL_MOVE_OUT(1, "人工出库"),

    ;

    private int value;

    private final String descCN;

}
