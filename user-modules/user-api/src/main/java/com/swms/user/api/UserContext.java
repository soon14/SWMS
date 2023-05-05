package com.swms.user.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserContext {
    public static final String ROLE_GRANTED_AUTHORITY_PREFIX = "$$$&&&!!!ROLE_";

    public static final String SUPPER_PERMISSION = "*:*";

    private static final String ANONYMOUS_USER = "unknownUser";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String USER_NAME = "user_name";
    private static final String AUTHORITIES = "authorities";

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
//            log.error("resolve the request error", e);
            return ANONYMOUS_USER;
        }

        String token = request.getHeader(AUTHORIZATION);
        if (token != null) {
            token = token.replace(BEARER, "");
            token = token.trim();
            try {
                DecodedJWT jwt = JWT.decode(token);
                return jwt.getClaim(USER_NAME).asString();
            } catch (Exception e) {
//                log.error("resolve the token error: {}", e.getMessage(), e);
                return ANONYMOUS_USER;
            }
        }
        return ANONYMOUS_USER;
    }

    /**
     * 获取当前用户的角色
     *
     * @return
     */
    public static Set<String> getCurrentRoleCodes() {
        Set<String> result = new HashSet<>();
        Set<String> roles = (getCurrentAuthorities()
            .stream()
            .filter(u -> u.startsWith(ROLE_GRANTED_AUTHORITY_PREFIX))
            .collect(Collectors.toSet()));
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(u -> {
                result.add(u.replace(ROLE_GRANTED_AUTHORITY_PREFIX, ""));
            });
        }
        return result;
    }

    /**
     * 获取当前用户的权限
     *
     * @return
     */
    public static Set<String> getCurrentPermissions() {
        return getCurrentAuthorities()
            .stream()
            .filter(u -> !u.startsWith(ROLE_GRANTED_AUTHORITY_PREFIX))
            .collect(Collectors.toSet());
    }

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return
     */
    public static Set<String> getCurrentAuthorities() {
        HttpServletRequest request;
        Set<String> result = new HashSet<>();
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
                result.addAll(jwt.getClaims().get(AUTHORITIES).asList(String.class));
            } catch (Exception e) {
                return result;
            }
        }
        return result;
    }
}
