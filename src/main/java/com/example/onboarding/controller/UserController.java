package com.example.onboarding.controller;

import com.example.onboarding.dto.LoginRequestDto;
import com.example.onboarding.dto.LoginResponseDto;
import com.example.onboarding.dto.SignupRequestDto;
import com.example.onboarding.dto.SignupResponseDto;
import com.example.onboarding.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/sign")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
