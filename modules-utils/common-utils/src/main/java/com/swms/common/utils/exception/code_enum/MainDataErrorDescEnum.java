package com.swms.common.utils.exception.code_enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MainDataErrorDescEnum implements IBaseError {

    /**
     * 主数据基本错误
     */
    MAIN_DATA_BASE_ERROR("MD001001", "main data base error"),

    CODE_MUST_NOT_UPDATE("MD001002", "code must not update"),

    /**
     * 仓库编码不存在通过code查询返回错误仓库编码
     */
    WAREHOUSE_CODE_NOT_EXIST("MD002001", "warehouse code {0} not exist"),
    /**
     * 仓库存在通过ID查询没有具体code
     */
    WAREHOUSE_NOT_EXIST("MD002001", "warehouse not exist"),


    /**
     * 客户编码不存在 可以返回code
     */
    OWNER_CODE_NOT_EXIST("MD003001", "owner code {0} not exist"),

    /**
     * 客户编码不存在值返回错误信息没有具体代码
     */
    OWNER_NOT_EXIST("MD003001", "owner not exist"),

    SKU_CODE_NOT_EXIST("MD004001", "sku code {0} not exist"),

    SOME_SKU_CODE_NOT_EXIST("MD004002", "some sku codes not exist"),

    BARCODE_PARSER_RESULT_CONFIG_NOT_MATCH("MD005001", "barcode parse result field size & config field size not match"),

    BARCODE_PARSE_RULE_REPEAT("MD005002", "barcode parse rule repeated");

    private final String code;

    private final String desc;


}
