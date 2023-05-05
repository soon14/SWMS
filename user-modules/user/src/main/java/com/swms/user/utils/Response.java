package com.swms.user.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 在使用这个返回对象的时候，如果是最简单的成功返回，可以这么使用：
 * <pre> {@code
 *  Response.builder().build();
 * }</pre>
 *
 * @author sws
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private transient static final Object DEFAULT_DATA = new Object();

    /**
     * 错误码
     * 错误码为字符串类型，共 5 位，分成两个部分：错误产生来源+四位数字编号
     * 错误产生来源分为 A/B/C，A 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付
     * 超时等问题；B 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；C 表示错误来源
     * 于第三方服务，比如 CDN 服务出错，消息投递超时等问题；四位数字编号从 0001 到 9999，大类之间的
     * 步长间距预留 100
     * <p>
     * 参考 阿里Java开发手册
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
    private T data;

    /**
     * 是前后端错误追踪机制的体现，可以在前端输出到 type="hidden"文字类控件中，或者用户端的日志中，帮助我们快速地定位出问题。
     */
    @Builder.Default
    private String errorMsg = "";

}
