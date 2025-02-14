package com.example.onboarding.security.jwt;

import lombok.Getter;

@Getter
public enum JwtType {
    ACCESS_TOKEN(60 * 60 * 1000L), // 60분
    REFRESH_TOKEN(60 * 60 * 24 * 7 * 1000L); // 1주
//    ACCESS_TOKEN(30  * 1000L), // 30초
//    REFRESH_TOKEN(60  * 60 * 1000L); // 60분

    private final long expirationTime;

    JwtType(long expirationTime){
        this.expirationTime = expirationTime;
    }
}
