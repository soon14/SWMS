package com.swms.user.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.swms.user.api.UserContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtUtils {

    @Value("${Jwt.jwtSecret:linsan}")
    private String jwtSecret;

    @Value("${Jwt.jwtExpirationMs:600000}")
    private int jwtExpirationMs;

    @Value("${Jwt.jwtCookieName:Authorization}")
    private String jwtCookie;

    @Value("${Jwt.claim.parameter.authorities:authorities}")
    private String authorities;

    public String getJwtFromRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);

        String jwt;
        if (cookie != null) {
            jwt = cookie.getValue();
        } else {
            jwt = request.getHeader(jwtCookie);
        }

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        return CompressUtils.decompress(Base64.decodeBase64(jwt));
    }


    public DecodedJWT verifyJwt(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
        return verifier.verify(token);
    }

    public String getUserNameFromJwtToken(String token) {
        DecodedJWT jwt = verifyJwt(token);
        return String.valueOf(jwt.getClaims().get(UserContext.USERNAME));
    }

    public String getUserNameFromJwtToken(DecodedJWT jwt) {
        return String.valueOf(jwt.getClaims().get(UserContext.USERNAME));
    }

    public String generateJwtCookie(List<String> authorities, String userName) {
        return Base64.encodeBase64String(CompressUtils.compress(generateToken(authorities, userName)));
    }

    public String generateToken(List<String> authorityList, String userName) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.create()
            .withClaim(UserContext.USERNAME, userName)
            .withClaim(authorities, authorityList)
            .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 1 hour
            .sign(algorithm);
    }
}
