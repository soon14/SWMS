package com.swms.common.error.code_enum;

import com.swms.common.error.IBaseError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库错误desc枚举
 *
 * @author krystal-2023
 * @date 2023/02/24
 */
@Getter
@AllArgsConstructor
public enum OutboundErrorDescEnum implements IBaseError {

    /**
     * 出站基本错误
     */
    OUTBOUND_BASE_ERROR("OUT001001", "outbound base error"),



    ;


    private final String code;
    private final String desc;


}
