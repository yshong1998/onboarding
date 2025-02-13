package com.example.onboarding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@NoArgsConstructor
public class SignupResponseDto {

    private String username;
    private String nickname;
    private Collection<GrantedAuthority> authorities;

    public SignupResponseDto(String username, String nickname, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.nickname = nickname;
        this.authorities = authorities;
    }

}
