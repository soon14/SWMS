package com.swms.user.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtUtils {

    @Value("${Jwt.jwtSecret:linsan}")
    private String jwtSecret;

    @Value("${Jwt.jwtExpirationMs:6000000}")
    private int jwtExpirationMs;

    @Value("${Jwt.claim.parameter.authorities:authorities}")
    private String authorities;

    public String generateJwtCookie(List<String> authorities, String userName) {
        return Base64.encodeBase64String(CompressUtils.compress(generateToken(authorities, userName)));
    }

    public String generateToken(List<String> authorityList, String userName) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.create()
            .withClaim("username", userName)
            .withClaim(authorities, authorityList)
            .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 1 hour
            .sign(algorithm);
    }
}
