package com.swms.user.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.swms.user.api.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserContext {
    public static final String ROLE_GRANTED_AUTHORITY_PREFIX = "ROLE_";

    public static final String SUPPER_PERMISSION = "*:*";

    private static final String ANONYMOUS_USER = "unknownUser";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String AUTHORITIES = "authorities";

    private static JwtUtils jwtUtils;

    @Autowired
    public UserContext(JwtUtils jwtUtils) {
        UserContext.jwtUtils = jwtUtils;
    }

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getCurrentUser() {
        HttpServletRequest request;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        } catch (Exception e) {
            log.error("resolve the request error", e);
            return ANONYMOUS_USER;
        }

        return jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromRequest(request));
    }

    /**
     * 获取当前用户的角色
     *
     * @return
     */
    public static Set<String> getCurrentRoleCodes() {
        return getCurrentAuthorities()
            .stream()
            .map(u -> u.getOrDefault("authority", "").toString())
            .filter(v -> v.startsWith(ROLE_GRANTED_AUTHORITY_PREFIX))
            .map(v -> v.replace(ROLE_GRANTED_AUTHORITY_PREFIX, ""))
            .collect(Collectors.toSet());
    }

    /**
     * 获取当前用户的权限
     *
     * @return
     */
    public static Set<String> getCurrentPermissions() {
        return getCurrentAuthorities()
            .stream()
            .filter(u -> !u.containsKey("permission"))
            .map(v -> v.getOrDefault("permission", "").toString())
            .collect(Collectors.toSet());
    }

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return
     */
    public static Set<Map> getCurrentAuthorities() {
        HttpServletRequest request;
        Set<Map> result = new HashSet<>();
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        } catch (Exception e) {
            return result;
        }

        String token = request.getHeader(AUTHORIZATION);
        if (token != null) {
            token = token.replace(BEARER, "");
            token = token.trim();
            try {
                DecodedJWT jwt = JWT.decode(token);
                List<Map> maps = jwt.getClaims().get(AUTHORITIES).asList(Map.class);
                result.addAll(maps);
            } catch (Exception e) {
                log.error("get current user authorities error", e);
                return result;
            }
        }
        return result;
    }
}
