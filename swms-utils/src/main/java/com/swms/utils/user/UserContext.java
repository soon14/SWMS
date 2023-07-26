package com.swms.utils.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class UserContext {
    public static final String ROLE_GRANTED_AUTHORITY_PREFIX = "ROLE_";
    public static final String SUPPER_PERMISSION = "*:*";
    public static final String ANONYMOUS_USER = "unknownUser";
    public static final String USERNAME = "username";

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getCurrentUser() {

        String username;
        HttpServletRequest request;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
            username = request.getHeader(USERNAME);
        } catch (Exception e) {
            log.error("resolve the request error", e);
            return ANONYMOUS_USER;
        }

        if (StringUtils.isEmpty(username)) {
            log.warn("request:{} has no username.", request.getRequestURI());
        }

        return username;
    }
}
