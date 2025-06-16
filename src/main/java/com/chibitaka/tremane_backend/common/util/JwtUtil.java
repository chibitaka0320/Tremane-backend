package com.chibitaka.tremane_backend.common.util;

import java.util.Date;

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

    /** アクセストークン作成 */
    public String createAccessToken(Long userId) {
        try {
            String token = JWT.create()
                    .withSubject(String.valueOf(userId))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .sign(algorithm);
            return token;
        } catch (JWTVerificationException e) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), null, e.getMessage());
        }
    }

    /** リフレッシュトークン作成 */
    public String createRefreshToken(Long userId) {
        try {
            String token = JWT.create()
                    .withSubject(String.valueOf(userId))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 525600 * 60 * 1000))
                    .sign(algorithm);
            return token;
        } catch (JWTVerificationException e) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), null, e.getMessage());
        }
    }

    /** ユーザーIDの抽出 */
    public Long extractUserId(String token) {
        try {
            DecodedJWT claim = JWT.require(algorithm).build().verify(token);
            Long userId = Long.parseLong(claim.getSubject());
            return userId;
        } catch (JWTVerificationException e) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), "10005E", e.getMessage());
        }
    }

    /** リフレッシュトークンの検証 */
    public boolean validateRefreshToken(String token) {
        try {
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
