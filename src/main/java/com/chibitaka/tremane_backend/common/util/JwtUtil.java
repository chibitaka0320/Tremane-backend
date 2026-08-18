package com.chibitaka.tremane_backend.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.chibitaka.tremane_backend.common.error.AuthenticationException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

/** JWT関連ユーティリティクラス */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    /** ユーザーIDの抽出 */
    public String extractUserId(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return decodedToken.getUid();
        } catch (FirebaseAuthException e) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), "10005E", e.getMessage());
        }
    }
}
