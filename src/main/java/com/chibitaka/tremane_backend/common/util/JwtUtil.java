package com.chibitaka.tremane_backend.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chibitaka.tremane_backend.common.error.AuthenticationException;

/** JWT関連ユーティリティクラス */
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private final Algorithm algorithm = Algorithm.HMAC256(secretKey);

    /** JWTトークン作成 */
    public String createJwtToken(Integer userId) {
        try {
            String token = JWT.create()
                    .withSubject(String.valueOf(userId))
                    .sign(algorithm);
            return token;
        } catch (JWTVerificationException e) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), null, e.getMessage());
        }
    }

    /** ユーザーIDの抽出 */
    public String extractUserId(String token) {
        try {
            DecodedJWT claim = JWT.require(algorithm).build().verify(token);
            String userId = claim.getSubject();
            return userId;
        } catch (JWTVerificationException e) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), null, e.getMessage());
        }
    }
}
