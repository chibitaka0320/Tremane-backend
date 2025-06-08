package com.chibitaka.tremane_backend.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chibitaka.tremane_backend.common.error.AuthenticationException;

/** JWT関連ユーティリティクラス */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    @SuppressWarnings("unused")
    private String secretKey;
    private Algorithm algorithm;

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        this.algorithm = Algorithm.HMAC256(secretKey); // setterが呼ばれた後に初期化
    }

    /** JWTトークン作成 */
    public String createJwtToken(Long userId) {
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
