package com.swms.common.utils.exception.code_enum;

import lombok.Getter;

@Getter
public enum MainDataErrorDescEnum implements IBaseError {

    /**
     * 主数据基本错误
     */
    MAIN_DATA_BASE_ERROR("MD001001", "main data base error"),

    CODE_MUST_NOT_UPDATE("MD001002", "code must not update"),

    WAREHOUSE_CODE_NOT_EXIST("MD002001", "warehouse code {0} not exist"),

    OWNER_CODE_NOT_EXIST("MD003001", "owner code {0} not exist"),

    SKU_CODE_NOT_EXIST("MD004001", "sku code {0} not exist"),

    SOME_SKU_CODE_NOT_EXIST("MD004002", "some sku codes not exist"),

    BARCODE_PARSER_RESULT_CONFIG_NOT_MATCH("MD005001", "barcode parse result field size & config field size not match"),

    BARCODE_PARSE_RULE_REPEAT("MD005002", "barcode parse rule repeated");

    private final String code;

    private final String desc;

    MainDataErrorDescEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
