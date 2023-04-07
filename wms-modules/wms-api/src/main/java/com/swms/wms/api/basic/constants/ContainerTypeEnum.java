package com.swms.wms.api.basic.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContainerTypeEnum {
    CONTAINER("料箱"),
    SHELF("料架"),
    TRANSFER_CONTAINER("周转容器"),
    PUT_WALL("播种墙");
    private final String descCN;
}
