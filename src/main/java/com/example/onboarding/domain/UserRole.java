package com.example.onboarding.domain;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum UserRole {

    ADMIN(Authority.ADMIN),
    USER(Authority.USER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public SimpleGrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(authority);
    }

    public static class Authority {

        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

}
