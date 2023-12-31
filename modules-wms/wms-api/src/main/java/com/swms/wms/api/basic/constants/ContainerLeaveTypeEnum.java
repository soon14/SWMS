package com.swms.wms.api.basic.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Dictionary
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
