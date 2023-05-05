package com.swms.user.config.security;

import com.swms.user.repository.entity.User;
import com.swms.user.service.CurrentUserService;
import com.swms.user.service.model.UserDetailsModel;
import com.swms.user.utils.TimeUtil;
import com.swms.utils.utils.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.ExecutorService;

/**
 * 授权成功监听者
 *
 * @author swms
 * @version 1.0
 */
@Component
public class AuthSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final ExecutorService executorService;
    private final CurrentUserService currentUserService;

    public AuthSuccessListener(@Qualifier("system") ExecutorService executorService,
                               CurrentUserService currentUserService) {
        this.executorService = executorService;
        this.currentUserService = currentUserService;
    }


    @Override
    public void onApplicationEvent(@NonNull AuthenticationSuccessEvent auth) {
        String ip = "";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            ip = IpUtil.getIpAddress(requestAttributes.getRequest());
        }
        Object principal = auth.getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsModel userDetailsModel) {
            User user = userDetailsModel.getUser();
            if (user == null) {
                return;
            }
            final String finalIp = ip;
            executorService.execute(() -> currentUserService.loginSuccess(user.getId(), user.getUsername(),
                finalIp, TimeUtil.getNowTimeStamp()));
        }
    }
}
