package com.swms.mdm.api.config.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Dictionary
public enum ParserObjectEnum {

    SKU_CODE("skuCode"),
    CONTAINER_CODE("containerCode"),
    CONTAINER_FACE("containerFace"),
    AMOUNT("amount"),
    INBOUND_DATE("inboundDate"),
    PRODUCT_DATE("productDate"),
    EXPIRED_DATE("expiredDate");

    private String value;
}
