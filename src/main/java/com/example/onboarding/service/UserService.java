package com.example.onboarding.service;

import com.example.onboarding.domain.User;
import com.example.onboarding.domain.UserDetailsImpl;
import com.example.onboarding.dto.LoginRequestDto;
import com.example.onboarding.dto.LoginResponseDto;
import com.example.onboarding.dto.SignupRequestDto;
import com.example.onboarding.dto.SignupResponseDto;
import com.example.onboarding.repository.UserRepository;
import com.example.onboarding.security.jwt.JwtUtil;
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

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = User.createUser(signupRequestDto, passwordEncoder);
        userRepository.save(user);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return new SignupResponseDto(user.getUsername(), user.getNickname(), userDetails.getAuthorities());
    }

    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
        return new LoginResponseDto(jwtUtil.substringToken(token));
    }
}
