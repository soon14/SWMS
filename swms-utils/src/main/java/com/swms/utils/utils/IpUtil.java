package com.swms.utils.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author guizhigang
 * @Date 2020/12/28 14:24
 * @Description: 常用获取客户端信息的工具
 */
@Slf4j
public class IpUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     *
     * @return
     *
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        try {
            // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
            ip = request.getHeader("X-Forwarded-For");
            if (!isEmpty(ip)) {
                return ip;
            }
            ip = request.getHeader("Proxy-Client-IP");
            if (!isEmpty(ip)) {
                return ip;
            }
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (!isEmpty(ip)) {
                return ip;
            }
            ip = request.getHeader("HTTP_CLIENT_IP");
            if (!isEmpty(ip)) {
                return ip;
            }
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (!isEmpty(ip)) {
                return ip;
            }
            return request.getRemoteAddr();
        } catch (Exception ex) {
            log.error("IpUtil#getIpAddress error", ex);
            return null;
        }
    }

    private static boolean isEmpty(String ip) {
        return ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip);
    }
}
