package com.swms.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContainerLeaveType {

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
