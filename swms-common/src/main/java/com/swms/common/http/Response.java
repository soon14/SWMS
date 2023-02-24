package com.swms.common.http;

import com.swms.common.error.IBaseError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    /**
     * @see IBaseError
     */
    @Builder.Default
    private String code = "0";

    /**
     * Code对应的msg，提示给用户
     */
    @Builder.Default
    private String msg = "success";

    /**
     * 存储返回前端的数据
     */
    private Object data;

    /**
     * 是前后端错误追踪机制的体现，可以在前端输出到 type="hidden"文字类控件中，或者用户端的日志中，帮助我们快速地定位出问题。
     */
    @Builder.Default
    private String detailMessage = "";

    public static Response success() {
        return Response.builder().code("0").msg("success").build();
    }
}
