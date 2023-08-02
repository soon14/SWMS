package com.swms.wms.api.inbound.constants;

import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcceptMethodEnum implements IEnum {

    BOX_CONTENT("box_content", "box_content"),
    LOOSE_INVENTORY("loose_inventory", "loose_inventory"),
    CONTAINER("container", "container");

    private String value;
    private String label;
}
