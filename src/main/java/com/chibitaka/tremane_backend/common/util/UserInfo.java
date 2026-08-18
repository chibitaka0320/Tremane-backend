package com.chibitaka.tremane_backend.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chibitaka.tremane_backend.common.error.AuthenticationException;

public class UserInfo {

    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof String) {
            String userId = (String) principal;
            return userId;
        } else {
            throw new AuthenticationException(null, "10007E", null);
        }
    }
}
