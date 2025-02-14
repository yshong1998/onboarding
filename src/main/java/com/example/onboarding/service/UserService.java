package com.example.onboarding.service;

import com.example.onboarding.domain.User;
import com.example.onboarding.domain.UserDetailsImpl;
import com.example.onboarding.domain.UserRole;
import com.example.onboarding.dto.SignupRequestDto;
import com.example.onboarding.dto.SignupResponseDto;
import com.example.onboarding.repository.UserRepository;
import com.example.onboarding.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignupResponseDto signup(
            SignupRequestDto signupRequestDto
    ) {
        User user = User.createUser(signupRequestDto, passwordEncoder);
        userRepository.save(user);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return new SignupResponseDto(user.getUsername(), user.getNickname(), userDetails.getAuthorities());
    }

    public void reissue(
            String refreshToken,
            HttpServletResponse response
    ) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Claims tokenClaims = jwtUtil.getClaimsFromToken(refreshToken);
        String username = tokenClaims.getSubject();
        String roleString = tokenClaims.get(JwtUtil.AUTHORIZATION_KEY, String.class);
        UserRole userRole = UserRole.valueOf(roleString);

        String newAccessToken = jwtUtil.createAccessToken(username, userRole);
        String newRefreshToken = jwtUtil.createRefreshToken(username, userRole);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken);
        response.addCookie(new Cookie(JwtUtil.REFRESH_TOKEN_KEY, newRefreshToken));
    }
}
