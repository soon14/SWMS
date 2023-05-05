package com.swms.user.config.security;

import com.swms.user.service.LoginLogService;
import com.swms.utils.utils.IpUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * 授权失败监听者
 *
 * @author swms
 * @version 1.0
 */
@Component
public class AuthFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    private static final String ACCESS_TOKEN = "access-token";

    private final ExecutorService executorService;
    private final LoginLogService loginLogService;

    public AuthFailureListener(@Qualifier("system") ExecutorService executorService,
                               LoginLogService loginLogService) {
        this.executorService = executorService;
        this.loginLogService = loginLogService;
    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent auth) {
        String ip = "";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            ip = IpUtil.getIpAddress(requestAttributes.getRequest());
        }
        Object username = auth.getAuthentication().getPrincipal();
        String s = String.valueOf(username);
        if (Objects.equals(s, ACCESS_TOKEN)) {
            return;
        }
        final String finalIp = ip;
        executorService.execute(() -> loginLogService.addFailure(String.valueOf(username), finalIp, auth.getTimestamp(),
            auth.getException().getLocalizedMessage()));
    }
}
