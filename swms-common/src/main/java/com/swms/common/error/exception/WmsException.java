package com.swms.common.error.exception;

import com.swms.common.error.IBaseError;
import lombok.*;

import java.util.function.Supplier;

/**
 * wms例外
 * wms exception
 *
 * @author krystal-2023
 * @date 2023/02/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class WmsException extends RuntimeException {
    /**
     * 代码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * wms例外
     *
     * @param message 消息
     */
    public WmsException(String message) {
        this.message = message;
    }

    /**
     * wms例外
     *
     * @param iBaseError 代码
     */
    public WmsException(IBaseError iBaseError) {
        this.code = iBaseError.getCode();
        this.message = iBaseError.getDesc();
    }


    /**
     * 抛出wms异常
     *
     * @param iBaseError 基本错误
     * @return {@link WmsException}
     */
    public static WmsException throwWmsException(IBaseError iBaseError) {
        return WmsException.builder()
                .code(iBaseError.getCode())
                .message(iBaseError.getDesc())
                .build();
    }

    /**
     * 抛出wms异常
     *
     * @param iBaseError 基本错误
     * @return {@link Supplier}<{@link WmsException}>
     */
    public static Supplier<WmsException> throwWmsExceptionSup(IBaseError iBaseError) {
        return () -> WmsException.builder()
                .code(iBaseError.getCode())
                .message(iBaseError.getDesc())
                .build();
    }

}
