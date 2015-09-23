package com.imdb.utils.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Authentication getAuthentication(boolean strict) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (strict && authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("Missing authentication object.");
        }
        return authentication;
    }
}
