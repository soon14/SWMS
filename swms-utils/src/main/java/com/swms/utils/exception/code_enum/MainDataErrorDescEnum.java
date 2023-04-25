package com.swms.utils.exception.code_enum;

import lombok.Getter;

@Getter
public enum MainDataErrorDescEnum implements IBaseError {

    /**
     * 主数据基本错误
     */
    MAIN_DATA_BASE_ERROR("MD001001", "main data base error"),

    CODE_MUST_NOT_UPDATE("MD001002", "code must not update"),

    BARCODE_PARSER_RESULT_CONFIG_NOT_MATCH("MD001003", "barcode parse result field size & config field size not match"),

    BARCODE_PARSE_RULE_REPEAT("MD001004", "barcode parse rule repeated");

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
