package com.swms.gateway.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Lists;
import com.swms.gateway.config.AuthProperties;
import com.swms.gateway.constant.SystemConstant;
import com.swms.gateway.util.ResponseUtil;
import com.swms.utils.compress.CompressUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author guizhigang
 * @Date 2021/5/17 15:22
 * @Description:
 */
@Slf4j
public class AuthGatewayFilter implements GlobalFilter, Ordered {

    /**
     * some url needn't be verified authentication
     */
    private static final List<String> USER_WHITE_AUTH_LIST = Lists.newArrayList(
        "/user/api/currentUser/getMenuTree",
        "/user/api/auth/signout",
        "/search/search"
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
        if (authorizeNotRequired) {
            return chain.filter(exchange);
        }

        // 无论是否需要鉴权，都要从请求头中的 Authorization 获得 jwt，进而获取当前用户名
        String token = exchange.getRequest().getHeaders().getFirst(SystemConstant.HEADER_AUTHORIZATION);
        if (token == null) {
            return unauthorized(exchange.getResponse(), "token not passed, please login.");
        }

        token = CompressUtils.decompress(Base64.decodeBase64(token));

        DecodedJWT jwt = null;
        try {
            jwt = verifyJwt(token);
        } catch (TokenExpiredException e) {
            return unauthorized(exchange.getResponse(), "token is expired.");
        }
        if (jwt == null) {
            return unauthorized(exchange.getResponse(), "token is not illegal.");
        }

        //authorization verify
        if (!USER_WHITE_AUTH_LIST.contains(requestUrl) && !verifyAuthorization(jwt, requestUrl)) {
            return unauthorized(exchange.getResponse(), "request access denied, may be unauthorized.");
        }

        //set username in request header
        ServerHttpRequest newRequest = exchange.getRequest().mutate()
            .header(SystemConstant.HEADER_AUTHORIZATION, "")
            .header(SystemConstant.USER_NAME, jwt.getClaim(SystemConstant.USER_NAME).asString()).build();
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
     * filter排序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
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

    public boolean verifyAuthorization(DecodedJWT jwt, String requestUrl) throws JWTVerificationException {
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
        return authoritySet.stream().anyMatch(requestUrl::startsWith);
    }

    public DecodedJWT verifyJwt(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SystemConstant.JWT_SECRET)).build();
        return verifier.verify(token);
    }
}
