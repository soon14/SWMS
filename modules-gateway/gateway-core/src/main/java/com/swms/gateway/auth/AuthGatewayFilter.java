package com.swms.gateway.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Lists;
import com.swms.common.utils.user.AuthConstants;
import com.swms.common.utils.utils.CompressUtils;
import com.swms.gateway.config.AuthProperties;
import com.swms.gateway.constant.SystemConstant;
import com.swms.gateway.util.ResponseUtil;
import com.swms.user.api.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * authorization and filter request
 */
@Slf4j
public class AuthGatewayFilter implements GlobalFilter, Ordered {

    /**
     * some url needn't be verified authentication
     */
    private static final List<String> USER_WHITE_AUTH_LIST = Lists.newArrayList(
        "/user/api/currentUser/getMenuTree",
        "/user/api/auth/signout",
        "/search/search",
        "/search/search/searchSelectResult",
        "/mdm/dictionary/getAll"
    );

    private final AuthProperties authProperties;

    private final JwtUtils jwtUtils;

    public AuthGatewayFilter(AuthProperties authProperties, JwtUtils jwtUtils) {
        this.authProperties = authProperties;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Token过滤器
     *
     * @param exchange exchange
     * @param chain    chain
     *
     * @return Mono
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //tenantId must be sent
        List<String> tenantIds = exchange.getRequest().getHeaders().get(SystemConstant.HEADER_TENANT_ID);
        if (CollectionUtils.isEmpty(tenantIds) || tenantIds.stream().filter(StringUtils::isNotEmpty).toList().isEmpty()) {
            return ResponseUtil.webFluxResponseWriter(exchange.getResponse(), SystemConstant.APPLICATION_JSON_UTF8,
                HttpStatus.BAD_REQUEST, "X-TenantID can't be empty.");
        }

        String requestUrl = exchange.getRequest().getURI().getRawPath();
        boolean authorizeNotRequired = ignore(requestUrl);
        if (authorizeNotRequired) {
            return chain.filter(exchange);
        }

        // 无论是否需要鉴权，都要从请求头中的 Authorization 获得 jwt，进而获取当前用户名
        String token = exchange.getRequest().getHeaders().getFirst(SystemConstant.HEADER_AUTHORIZATION);
        if (token == null) {
            return unauthorized(exchange.getResponse(), "token not passed, please login.");
        }

        token = CompressUtils.decompress(Base64.decodeBase64(token));

        DecodedJWT jwt;
        try {
            jwt = jwtUtils.verifyJwt(token);
        } catch (TokenExpiredException e) {
            return unauthorized(exchange.getResponse(), "token is expired.");
        }
        if (jwt == null) {
            return unauthorized(exchange.getResponse(), "token is not illegal.");
        }

        String tenantId = tenantIds.get(0);
        if (!verifyAuthTenant(jwt, tenantId)) {
            return unauthorized(exchange.getResponse(), "request access tenant: " + tenantId + " denied, user maybe other tenant.");
        }

        //verify authorization
        if (!USER_WHITE_AUTH_LIST.contains(requestUrl) && !verifyAuthorization(jwt, requestUrl, exchange.getRequest().getMethod())) {
            return unauthorized(exchange.getResponse(), "request access denied, may be unauthorized.");
        }

        //verify auth warehouse
        List<String> warehouseCodes = exchange.getRequest().getHeaders().get(SystemConstant.HEADER_WAREHOUSE);
        if (CollectionUtils.isNotEmpty(warehouseCodes)) {
            String warehouseCode = warehouseCodes.get(0);
            if (StringUtils.isNotEmpty(warehouseCode) && !"null".equals(warehouseCode.trim()) && !verifyAuthWarehouse(jwt, warehouseCode)) {
                return unauthorized(exchange.getResponse(), "request access warehouse: " + warehouseCode + " denied, may be unauthorized.");
            }
        }

        //set username in request header
        ServerHttpRequest newRequest = exchange.getRequest().mutate()
            .header(SystemConstant.HEADER_AUTHORIZATION, "")
            .header(AuthConstants.USERNAME, jwt.getClaim(AuthConstants.USERNAME).asString()).build();
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    private boolean verifyAuthTenant(DecodedJWT jwt, String tenantId) {
        Claim claim = jwt.getClaim(AuthConstants.AUTH_TENANT);
        if (null == claim) {
            return false;
        }
        String authTenant = claim.asString();
        return StringUtils.equals(authTenant, tenantId);
    }

    private boolean verifyAuthWarehouse(DecodedJWT jwt, String warehouseCode) {
        Claim claim = jwt.getClaim(AuthConstants.AUTH_WAREHOUSE);
        if (null == claim) {
            return false;
        }
        List<String> authWarehouseList = claim.asList(String.class);
        if (CollectionUtils.isEmpty(authWarehouseList)) {
            return false;
        }

        return authWarehouseList.contains(warehouseCode);

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

    private Mono<Void> unauthorized(ServerHttpResponse resp, String msg) {
        return ResponseUtil.webFluxResponseWriter(resp, SystemConstant.APPLICATION_JSON_UTF8, HttpStatus.UNAUTHORIZED, msg);
    }

    public boolean verifyAuthorization(DecodedJWT jwt, String requestUrl, HttpMethod httpMethod) throws JWTVerificationException {
        Claim authorities = jwt.getClaim(AuthConstants.AUTH_MENUS);
        if (null == authorities) {
            return false;
        }
        List<String> authoritySet = authorities.asList(String.class);
        if (CollectionUtils.isEmpty(authoritySet)) {
            return false;
        }
        if (authoritySet.contains(AuthConstants.SUPPER_PERMISSION)) {
            return true;
        }
        String url = httpMethod.name().toLowerCase() + ":" + requestUrl;
        return authoritySet.stream().anyMatch(url::startsWith);
    }

}
