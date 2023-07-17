package com.swms.wms.api.basic.constants;

import com.swms.utils.dictionary.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Dictionary
public enum ContainerTypeEnum {
    CONTAINER("料箱"),
    SHELF("料架"),
    PUT_WALL("播种墙");
    private final String descCN;
}
