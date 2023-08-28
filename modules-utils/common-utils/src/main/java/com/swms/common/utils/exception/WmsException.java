package com.swms.common.utils.exception;

import com.swms.common.utils.exception.code_enum.IBaseError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.function.Supplier;

/**
 * wms例外
 * wms exception
 *
 * @author sws
 * @date 2023/02/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WmsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8192643659977363045L;

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
     *
     * @return {@link WmsException}
     */
    public static WmsException throwWmsException(IBaseError iBaseError) {
        return WmsException.builder()
            .code(iBaseError.getCode())
            .message(iBaseError.getDesc())
            .build();
    }


    /**
     * 抛出wms异常，带有具体字段错误信息
     *
     * @param iBaseError 基本错误
     * @param args       具体参数
     *
     * @return {@link WmsException}
     */
    public static WmsException throwWmsException(IBaseError iBaseError, Object... args) {
        return WmsException.builder()
            .code(iBaseError.getCode())
            .message(MessageFormat.format(iBaseError.getDesc(), args))
            .build();
    }

    /**
     * 抛出wms异常
     *
     * @param iBaseError 基本错误
     *
     * @return {@link Supplier}<{@link WmsException}>
     */
    public static Supplier<WmsException> throwWmsExceptionSup(IBaseError iBaseError) {
        return () -> WmsException.builder()
            .code(iBaseError.getCode())
            .message(iBaseError.getDesc())
            .build();
    }

    /**
     * 抛出wms异常,带有具体字段错误信息
     *
     * @param iBaseError 基本错误
     * @param args       参数
     *
     * @return {@link Supplier}<{@link WmsException}>
     */
    public static Supplier<WmsException> throwWmsExceptionSup(IBaseError iBaseError, Object... args) {
        return () -> WmsException.builder()
            .code(iBaseError.getCode())
            .message(MessageFormat.format(iBaseError.getDesc(), args))
            .build();
    }

}
