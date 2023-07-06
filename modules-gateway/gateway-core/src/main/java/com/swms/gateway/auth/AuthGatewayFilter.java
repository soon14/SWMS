package com.swms.gateway.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Lists;
import com.swms.gateway.auth.verify.ClockImpl;
import com.swms.gateway.auth.verify.JWTVerifierExtendTimeImpl;
import com.swms.gateway.config.AuthProperties;
import com.swms.gateway.constant.SystemConstant;
import com.swms.gateway.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author guizhigang
 * @Date 2021/5/17 15:22
 * @Description:
 */
@Slf4j
public class AuthGatewayFilter implements GlobalFilter, Ordered {

    private static final List<String> USER_WHITE_LIST = Lists.newArrayList(
        "/user/api/currentUser/searchMenuTree",
        "/user/api/user/info",
        "/user/api/user/nav",
        "/user/api/auth/signin",
        "/user/external/logout"
    );

    private AuthProperties authProperties;

    private Algorithm algorithm;

    public AuthGatewayFilter(AuthProperties authProperties) {
        this.algorithm = Algorithm.HMAC256(authProperties.getSigningKey());
        this.authProperties = authProperties;
    }

    /**
     * Token过滤器
     *
     * @param exchange
     * @param chain
     *
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如果未启用网关验证，则跳过
        if (Boolean.FALSE.equals(authProperties.getEnable())) {
            return chain.filter(exchange);
        }

        String requestUrl = exchange.getRequest().getURI().getRawPath();
        // 网关国际化语言标记传递,我们业务定义的lang 覆盖 浏览器传入的
        reCoverLangTarget(exchange);
        boolean authorizeNotRequired = ignore(requestUrl);
        //如是登出请求直接登出防止由于网关续时导致致退出失败
        if (StringUtils.equals(SystemConstant.LOGOUT_URL, requestUrl)) {
            return ResponseUtil.webFluxResponseWriter(exchange.getResponse(), SystemConstant.APPLICATION_JSON_UTF8,
                HttpStatus.OK, SystemConstant.LOGOUT_MSG);
        }
        if (authorizeNotRequired && USER_WHITE_LIST.contains(requestUrl)) {
            return chain.filter(exchange);
        }

        String username = SystemConstant.ANONYMOUS_USER;

        // 无论是否需要鉴权，都要从请求头中的 Authorization 获得 jwt，进而获取当前用户名
        ServerHttpResponse resp = exchange.getResponse();
        String headerToken = exchange.getRequest().getHeaders().getFirst(SystemConstant.HEADER_AUTHORIZATION);
        DecodedJWT jwt = null;

        if (headerToken != null) {
            //重写解析token逻辑进行操作续时
            jwt = resolveToken(headerToken);
            if (jwt != null) {
                username = jwt.getClaim(SystemConstant.USER_NAME).asString();
            }
        }

        if (!authorizeNotRequired) {
            //鉴权
            Mono<Void> monoResp = authenticate(exchange, resp, headerToken, jwt);
            if (monoResp != null) {
                return monoResp;
            }
        }

        // user应用保留token，非user应用，去掉原token中的权限角色等数据，只保留用户名，减少token大小
        if (requestUrl.toLowerCase().startsWith("/user/")) {
            return chain.filter(exchange);
        }

        String newToken = JWT.create()
            .withClaim(SystemConstant.USER_NAME, username)
            .sign(this.algorithm);

        ServerHttpRequest newRequest = exchange.getRequest().mutate()
            .header(SystemConstant.HEADER_AUTHORIZATION, SystemConstant.TOKEN_BEARER + " " + newToken)
            .build();
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    private void reCoverLangTarget(ServerWebExchange exchange) {
        try {
            HttpHeaders httpHeaders = HttpHeaders.writableHttpHeaders(exchange.getRequest().getHeaders());
            List<String> langs = httpHeaders.get(SystemConstant.LANG);
            if (!CollectionUtils.isEmpty(langs)) {
                httpHeaders.set(SystemConstant.ACCEPT_LANGUAGE, langs.get(0).replace("_", "-"));
            }
        } catch (Exception e) {
            log.error("重覆盖国际化语言头异常", e);
        }
    }

    /**
     * 鉴权
     *
     * @param exchange    服务网络交换器
     * @param resp        响应结果
     * @param headerToken 请求头中的token
     * @param jwt         解码token后的解码对象
     *
     * @return 返回结果
     */
    private Mono<Void> authenticate(ServerWebExchange exchange, ServerHttpResponse resp, String headerToken, DecodedJWT jwt) {
        // 对于需要鉴权的 url，token 与 jwt 均不能为空
        if (headerToken == null) {
            return unauthorized(resp, "Invalid Token");
        }

        if (jwt == null) {
            return unauthorized(resp, "Invalid Token");
        }

        //本地缓存全局变量中获取超时时间戳
        Date expiresAt = JWTVerifierExtendTimeImpl.EXTENDTIME_CACHE_MAP.get(jwt.getToken());
        if (expiresAt == null) {
            expiresAt = jwt.getExpiresAt();
        }
        Date today = new Date();
        // 鉴权
        if (today.after(expiresAt)) {
            return unauthorized(resp, "Token expired");
        }
        if (!hasPermission(jwt, exchange)) {
            return ResponseUtil.webFluxResponseWriter(resp, SystemConstant.APPLICATION_JSON_UTF8, HttpStatus.FORBIDDEN, "unauthorized");
        }
        return null;
    }

    /**
     * filter排序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    /**
     * 解析JWT Token
     *
     * @param token
     *
     * @return
     */
    private DecodedJWT resolveToken(String token) {
        try {
            JWTVerifierExtendTimeImpl verifierExtendTime = new JWTVerifierExtendTimeImpl(algorithm, new HashMap<>(), new ClockImpl());
            if (token.startsWith(SystemConstant.TOKEN_BEARER)) {
                token = token.replace(SystemConstant.TOKEN_BEARER, "");
            }
            token = token.trim();
            return verifierExtendTime.verify(token, authProperties.getExtendTimeSecond());
        } catch (Exception e) {
            log.error("AuthGatewayFilter#verify error. token:{}", token, e);
            return null;
        }
    }

    /**
     * 检查是否忽略url
     *
     * @param path 路径
     *
     * @return boolean
     */
    private boolean ignore(String path) {
        if (path == null) {
            return true;
        }
        List<String> ignoreUrls = authProperties.getIgnoreUrl();
        if (CollectionUtils.isEmpty(ignoreUrls)) {
            return false;
        }
        for (String url : ignoreUrls) {
            if (StringUtils.isBlank(url)) {
                continue;
            }
            if (path.toLowerCase().startsWith(url.replace("/**", "").toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有权限
     *
     * @param jwt
     * @param exchange
     *
     * @return
     */
    private boolean hasPermission(DecodedJWT jwt, ServerWebExchange exchange) {
        Claim authorities = jwt.getClaim(SystemConstant.JWT_AUTHORITIES);
        if (null == authorities) {
            return false;
        }
        List<String> authoritySet = authorities.asList(String.class);
        if (CollectionUtils.isEmpty(authoritySet)) {
            return false;
        }
        if (authoritySet.contains(SystemConstant.SUPPER_PERMISSION)) {
            return true;
        }
        String path = exchange.getRequest().getURI().getRawPath();
        return authoritySet.stream().anyMatch(path::startsWith);
    }

    /**
     * 无权限时返回
     *
     * @param resp
     * @param msg
     *
     * @return
     */
    private Mono<Void> unauthorized(ServerHttpResponse resp, String msg) {
        return ResponseUtil.webFluxResponseWriter(resp, SystemConstant.APPLICATION_JSON_UTF8, HttpStatus.UNAUTHORIZED, msg);
    }
}
