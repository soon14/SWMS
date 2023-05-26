package com.swms.gateway.auth.verify;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Header;
import com.auth0.jwt.interfaces.Payload;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JWTExtendTimeDecoder implements DecodedJWT, Serializable {

    private static final long serialVersionUID = 1873362438023312895L;

    private final String[] parts;
    private final Header header;
    private final Payload payload;


    JWTExtendTimeDecoder(String jwt) throws JWTDecodeException {
        this(new JWTParser(), jwt);
    }

    /**
     * Splits the given token on the "." chars into a String array with 3 parts.
     *
     * @param token the string to split.
     *
     * @return the array representing the 3 parts of the token.
     *
     * @throws JWTDecodeException if the Token doesn't have 3 parts.
     */
    static String[] splitToken(String token) throws JWTDecodeException {
        String[] parts = token.split("\\.");
        if (parts.length == 2 && token.endsWith(".")) {
            //Tokens with alg='none' have empty String as Signature.
            parts = new String[]{parts[0], parts[1], ""};
        }
        if (parts.length != 3) {
            throw new JWTDecodeException(String.format("The token was expected to have 3 parts, but got %s.", parts.length));
        }
        return parts;
    }

    JWTExtendTimeDecoder(JWTParser converter, String jwt) throws JWTDecodeException {
        parts = splitToken(jwt);
        String headerJson;
        String payloadJson;
        try {
            headerJson = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        } catch (NullPointerException e) {
            throw new JWTDecodeException("The UTF-8 Charset isn't initialized.", e);
        } catch (IllegalArgumentException e) {
            throw new JWTDecodeException("The input is not a valid base 64 encoded string.", e);
        }
        header = converter.parseHeader(headerJson);
        payload = converter.parsePayload(payloadJson);

    }


    @Override
    public String getAlgorithm() {
        return header.getAlgorithm();
    }

    @Override
    public String getType() {
        return header.getType();
    }

    @Override
    public String getContentType() {
        return header.getContentType();
    }

    @Override
    public String getKeyId() {
        return header.getKeyId();
    }

    @Override
    public Claim getHeaderClaim(String name) {
        return header.getHeaderClaim(name);
    }

    @Override
    public String getIssuer() {
        return payload.getIssuer();
    }

    @Override
    public String getSubject() {
        return payload.getSubject();
    }

    @Override
    public List<String> getAudience() {
        return payload.getAudience();
    }

    @Override
    public Date getExpiresAt() {
        return payload.getExpiresAt();
    }

    @Override
    public Date getNotBefore() {
        return payload.getNotBefore();
    }

    @Override
    public Date getIssuedAt() {
        return payload.getIssuedAt();
    }

    @Override
    public String getId() {
        return payload.getId();
    }

    @Override
    public Claim getClaim(String name) {
        return payload.getClaim(name);
    }

    @Override
    public Map<String, Claim> getClaims() {
        return payload.getClaims();
    }

    @Override
    public String getHeader() {
        return parts[0];
    }

    @Override
    public String getPayload() {
        return parts[1];
    }

    @Override
    public String getSignature() {
        return parts[2];
    }

    @Override
    public String getToken() {
        return String.format("%s.%s.%s", parts[0], parts[1], parts[2]);
    }
}
