package com.swms.user.api.utils;

import static com.swms.user.api.dto.constants.AuthConstants.AUTH_MENUS;
import static com.swms.user.api.dto.constants.AuthConstants.AUTH_TENANT;
import static com.swms.user.api.dto.constants.AuthConstants.AUTH_WAREHOUSE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Lists;
import com.swms.utils.user.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class JwtUtils {

    @Value("${Jwt.jwtSecret:linsan}")
    private String jwtSecret;

    @Value("${Jwt.jwtExpirationMs:6000000}")
    private int jwtExpirationMs;

    public String generateJwtCookie(List<String> authorities, String userName, Set<String> authWarehouseCodes, String tenantName) {
        return Base64.encodeBase64String(CompressUtils.compress(generateToken(authorities, userName, authWarehouseCodes, tenantName)));
    }

    public String generateToken(List<String> authorityList, String userName, Set<String> authWarehouseCodes, String tenantName) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.create()
            .withClaim(AUTH_TENANT, tenantName)
            .withClaim(UserContext.USERNAME, userName)
            .withClaim(AUTH_MENUS, authorityList)
            .withClaim(AUTH_WAREHOUSE, Lists.newArrayList(authWarehouseCodes))
            .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 1 hour
            .sign(algorithm);
    }

    public DecodedJWT verifyJwt(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
        return verifier.verify(token);
    }
}
