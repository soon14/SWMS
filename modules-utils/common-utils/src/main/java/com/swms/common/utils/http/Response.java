package com.swms.common.utils.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Response<T> {

    /**
     * 默认成功代码
     */
    public static final String SUCCESS_CODE = "0";
    /**
     * 默认成功信息
     */
    private static final String SUCCESS_MSG = "success";
    /**
     * 默认失败代码
     */
    private static final String FAIL_CODE = "1";
    /**
     * 默认失败信息
     */
    private static final String FAILED_MSG = "failed";


    /**
     *
     */
    @Builder.Default
    private String code = SUCCESS_CODE;

    /**
     * Code对应的msg，提示给用户
     */
    @Builder.Default
    private String msg = SUCCESS_MSG;

    /**
     * 存储返回前端的数据
     */
    private T data;

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Response<T> success() {
        return ((Response<T>) Response.builder().code(SUCCESS_CODE).msg(SUCCESS_MSG).build());
    }

    public static <T> Response<T> success(T t) {
        final Response<T> success = success();
        success.setData(t);
        return success;
    }

}
