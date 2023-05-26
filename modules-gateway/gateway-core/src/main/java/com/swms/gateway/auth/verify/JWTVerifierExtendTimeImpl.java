package com.swms.gateway.auth.verify;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.impl.NullClaim;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 重写access_token的校验，为一直操作的用户自动续时
 */
public class JWTVerifierExtendTimeImpl implements JWTVerifier {

    private final Algorithm algorithm;
    final Map<String, Object> claims;
    private final Clock clock;
    private final JWTParser parser;

    static final String AUDIENCE_EXACT = "AUDIENCE_EXACT";
    static final String AUDIENCE_CONTAINS = "AUDIENCE_CONTAINS";

    /**
     * 续时本地缓存
     */
    public static final Map<String, Date> EXTENDTIME_CACHE_MAP = new ConcurrentHashMap();


    public JWTVerifierExtendTimeImpl(Algorithm algorithm, Map<String, Object> claims, Clock clock) {
        long defaultLeeway = 0;
        if (!claims.containsKey(PublicClaims.EXPIRES_AT)) {
            claims.put(PublicClaims.EXPIRES_AT, defaultLeeway);
        }
        if (!claims.containsKey(PublicClaims.NOT_BEFORE)) {
            claims.put(PublicClaims.NOT_BEFORE, defaultLeeway);
        }
        if (!claims.containsKey(PublicClaims.ISSUED_AT)) {
            claims.put(PublicClaims.ISSUED_AT, defaultLeeway);
        }
        this.algorithm = algorithm;
        this.claims = Collections.unmodifiableMap(claims);
        this.clock = clock;
        this.parser = new JWTParser();
    }


    /**
     * Perform the verification against the given Token, using any previous configured options.
     *
     * @param token to verify.
     *
     * @return a verified and decoded JWT.
     *
     * @throws AlgorithmMismatchException     if the algorithm stated in the token's header it's not equal to the one defined in the {@link com.auth0.jwt.JWTVerifier}.
     * @throws SignatureVerificationException if the signature is invalid.
     * @throws TokenExpiredException          if the token has expired.
     * @throws InvalidClaimException          if a claim contained a different value than the expected one.
     */
    @Override
    public DecodedJWT verify(String token) throws JWTVerificationException {
        DecodedJWT jwt = new JWTExtendTimeDecoder(parser, token);
        return verify(jwt);
    }

    /**
     * 续时验证
     *
     * @param token
     * @param extendTimeSecond
     *
     * @return
     *
     * @throws JWTVerificationException
     */
    public DecodedJWT verify(String token, long extendTimeSecond) throws JWTVerificationException {
        DecodedJWT jwt = new JWTExtendTimeDecoder(parser, token);
        //进行续时
        extendTime(jwt, extendTimeSecond);
        return verify(jwt);
    }

    /**
     * Perform the verification against the given decoded JWT, using any previous configured options.
     *
     * @param jwt to verify.
     *
     * @return a verified and decoded JWT.
     *
     * @throws AlgorithmMismatchException     if the algorithm stated in the token's header it's not equal to the one defined in the {@link com.auth0.jwt.JWTVerifier}.
     * @throws SignatureVerificationException if the signature is invalid.
     * @throws TokenExpiredException          if the token has expired.
     * @throws InvalidClaimException          if a claim contained a different value than the expected one.
     */
    @Override
    public DecodedJWT verify(DecodedJWT jwt) throws JWTVerificationException {
        verifyAlgorithm(jwt, algorithm);
        algorithm.verify(jwt);
        verifyClaims(jwt, claims);
        return jwt;
    }


    /**
     * gateway针对已登录常操作用户进行续时
     *
     * @param jwt
     */
    private void extendTime(DecodedJWT jwt, long extendTimeSecond) {
        //检查并清理超时的access_token
        checkAndRemoveTimeoutToken();
        //初始化为当前时间
        Date today = new Date();
        String accessToke = jwt.getToken();
        if (EXTENDTIME_CACHE_MAP.containsKey(accessToke)) {
            //续时
            today.setTime(today.getTime() + extendTimeSecond * 1000);
            EXTENDTIME_CACHE_MAP.put(accessToke, today);
        } else {
            Date expiresAt = jwt.getExpiresAt();
            //当前时间小于超时时间戳，则初始化为token自带超时时间戳
            if (expiresAt != null && today.before(expiresAt)) {
                EXTENDTIME_CACHE_MAP.put(accessToke, expiresAt);
            }
        }

    }

    /**
     * 清理超时的accessToken，释放内存
     */
    private void checkAndRemoveTimeoutToken() {
        Date today = new Date();
        EXTENDTIME_CACHE_MAP.forEach((accessToken, date) -> {
            if (today.after(date)) {
                EXTENDTIME_CACHE_MAP.remove(accessToken);
            }
        });
    }


    private void verifyAlgorithm(DecodedJWT jwt, Algorithm expectedAlgorithm) throws AlgorithmMismatchException {
        if (!expectedAlgorithm.getName().equals(jwt.getAlgorithm())) {
            throw new AlgorithmMismatchException("The provided Algorithm doesn't match the one defined in the JWT's Header.");
        }
    }

    private void verifyClaims(DecodedJWT jwt, Map<String, Object> claims) throws TokenExpiredException, InvalidClaimException {
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            if (entry.getValue() instanceof NonEmptyClaim) {
                assertClaimPresent(jwt.getClaim(entry.getKey()), entry.getKey());
            } else {
                verifyClaimValues(jwt, entry);
            }
        }
    }

    private void verifyClaimValues(DecodedJWT jwt, Map.Entry<String, Object> expectedClaim) {
        switch (expectedClaim.getKey()) {
            // We use custom keys for audience in the expected claims to differentiate between validating that the audience
            // contains all expected values, or validating that the audience contains at least one of the expected values.
            case AUDIENCE_EXACT:
                assertValidAudienceClaim(jwt.getAudience(), (List<String>) expectedClaim.getValue(), true);
                break;
            case AUDIENCE_CONTAINS:
                assertValidAudienceClaim(jwt.getAudience(), (List<String>) expectedClaim.getValue(), false);
                break;
            case PublicClaims.EXPIRES_AT:
                //本地缓存全局变量中获取
                Date expiresAt = EXTENDTIME_CACHE_MAP.get(jwt.getToken());
                if (expiresAt == null) {
                    expiresAt = jwt.getExpiresAt();
                }
                assertValidDateClaim(expiresAt, (Long) expectedClaim.getValue(), true);
                break;
            case PublicClaims.ISSUED_AT:
                assertValidDateClaim(jwt.getIssuedAt(), (Long) expectedClaim.getValue(), false);
                break;
            case PublicClaims.NOT_BEFORE:
                assertValidDateClaim(jwt.getNotBefore(), (Long) expectedClaim.getValue(), false);
                break;
            case PublicClaims.ISSUER:
                assertValidIssuerClaim(jwt.getIssuer(), (List<String>) expectedClaim.getValue());
                break;
            case PublicClaims.JWT_ID:
                assertValidStringClaim(expectedClaim.getKey(), jwt.getId(), (String) expectedClaim.getValue());
                break;
            case PublicClaims.SUBJECT:
                assertValidStringClaim(expectedClaim.getKey(), jwt.getSubject(), (String) expectedClaim.getValue());
                break;
            default:
                assertValidClaim(jwt.getClaim(expectedClaim.getKey()), expectedClaim.getKey(), expectedClaim.getValue());
                break;
        }
    }

    private void assertClaimPresent(Claim claim, String claimName) {
        if (claim instanceof NullClaim) {
            throw new InvalidClaimException(String.format("The Claim '%s' is not present in the JWT.", claimName));
        }
    }

    private void assertValidClaim(Claim claim, String claimName, Object value) {
        boolean isValid = false;
        if (value instanceof String) {
            isValid = value.equals(claim.asString());
        } else if (value instanceof Integer) {
            isValid = value.equals(claim.asInt());
        } else if (value instanceof Long) {
            isValid = value.equals(claim.asLong());
        } else if (value instanceof Boolean) {
            isValid = value.equals(claim.asBoolean());
        } else if (value instanceof Double) {
            isValid = value.equals(claim.asDouble());
        } else if (value instanceof Date) {
            isValid = value.equals(claim.asDate());
        } else if (value instanceof Object[]) {
            List<Object> claimArr;
            Object[] claimAsObject = claim.as(Object[].class);

            // Jackson uses 'natural' mapping which uses Integer if value fits in 32 bits.
            if (value instanceof Long[]) {
                // convert Integers to Longs for comparison with equals
                claimArr = new ArrayList<>(claimAsObject.length);
                for (Object cao : claimAsObject) {
                    if (cao instanceof Integer) {
                        claimArr.add(((Integer) cao).longValue());
                    } else {
                        claimArr.add(cao);
                    }
                }
            } else {
                claimArr = claim.isNull() ? Collections.emptyList() : Arrays.asList(claim.as(Object[].class));
            }
            List<Object> valueArr = Arrays.asList((Object[]) value);
            isValid = claimArr.containsAll(valueArr);
        }

        if (!isValid) {
            throw new InvalidClaimException(String.format("The Claim '%s' value doesn't match the required one.", claimName));
        }
    }

    private void assertValidStringClaim(String claimName, String value, String expectedValue) {
        if (!expectedValue.equals(value)) {
            throw new InvalidClaimException(String.format("The Claim '%s' value doesn't match the required one.", claimName));
        }
    }

    private void assertValidDateClaim(Date date, long leeway, boolean shouldBeFuture) {
        Date today = new Date(clock.getToday().getTime());
        today.setTime(today.getTime() / 1000 * 1000); // truncate millis
        if (shouldBeFuture) {
            assertDateIsFuture(date, leeway, today);
        } else {
            assertDateIsPast(date, leeway, today);
        }
    }

    private void assertDateIsFuture(Date date, long leeway, Date today) {
        today.setTime(today.getTime() - leeway * 1000);
        if (date != null && today.after(date)) {
            throw new TokenExpiredException(String.format("The Token has expired on %s.", date));
        }
    }

    private void assertDateIsPast(Date date, long leeway, Date today) {
        today.setTime(today.getTime() + leeway * 1000);
        if (date != null && today.before(date)) {
            throw new InvalidClaimException(String.format("The Token can't be used before %s.", date));
        }
    }

    private void assertValidAudienceClaim(List<String> audience, List<String> values, boolean shouldContainAll) {
        if (audience == null || (shouldContainAll && !audience.containsAll(values)) ||
            (!shouldContainAll && Collections.disjoint(audience, values))) {
            throw new InvalidClaimException("The Claim 'aud' value doesn't contain the required audience.");
        }
    }

    private void assertValidIssuerClaim(String issuer, List<String> value) {
        if (issuer == null || !value.contains(issuer)) {
            throw new InvalidClaimException("The Claim 'iss' value doesn't match the required issuer.");
        }
    }

    /**
     * Simple singleton used to mark that a claim should only be verified for presence.
     */
    private static class NonEmptyClaim {
        private static NonEmptyClaim nonEmptyClaim;

        private NonEmptyClaim() {
        }

        public static NonEmptyClaim getInstance() {
            if (nonEmptyClaim == null) {
                nonEmptyClaim = new NonEmptyClaim();
            }
            return nonEmptyClaim;
        }
    }


}
