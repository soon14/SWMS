package com.swms.wms.api.basic.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Dictionary
public enum ContainerStatusEnum {

    CREATED("创建"),

    /**
     * 待上架
     */
    WAIT_PUT_AWAY("待上架"),

    /**
     * 已入库
     */
    PUT_AWAY("已入库"),

    /**
     * 在库外
     */
    OUT_SIDE("在库外");

    private final String descCN;
}
