package com.swms.user.api.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwtSecret:linsan}")
    private String jwtSecret;

    @Value("${jwtExpirationMs:600000}")
    private int jwtExpirationMs;

    @Value("${bezkoder.app.jwtCookieName:Authorization}")
    private String jwtCookie;

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

        return jwt;
    }

    public ResponseCookie generateJwtCookie(UserDetails userPrincipal) {
        String jwt = generateToken(userPrincipal);
        return ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(3600).httpOnly(true).build();
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, null).path("/api").build();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("username", String.class);
    }

    public Set getAuthoritiesFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("authorities", Set.class);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("username", userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities());
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
}
