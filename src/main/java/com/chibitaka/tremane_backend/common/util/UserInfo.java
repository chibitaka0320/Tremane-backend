package com.chibitaka.tremane_backend.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chibitaka.tremane_backend.common.error.AuthenticationException;

public class UserInfo {

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof Long) {
            Long userId = (Long) principal;
            return userId;
        } else {
            throw new AuthenticationException(null, "10007E", null);
        }
    }
}
