package com.swms.common.utils.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CommonException extends RuntimeException {

    private final String message;

    private Object[] objArgs;

    public CommonException(String message) {
        this.message = message;
    }

    public CommonException(String message, Object... objArgs) {
        this.message = message;
        this.objArgs = objArgs;
    }
}
