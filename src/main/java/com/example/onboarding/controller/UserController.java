package com.example.onboarding.controller;

import com.example.onboarding.dto.SignupRequestDto;
import com.example.onboarding.dto.SignupResponseDto;
import com.example.onboarding.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponseDto signup(
            @RequestBody SignupRequestDto signupRequestDto
    ) {
        return userService.signup(signupRequestDto);
    }

    @GetMapping("/reissue")
    public void reissue(
            @CookieValue(name = "refresh") String refreshToken,
            HttpServletResponse response) {
        userService.reissue(refreshToken, response);
    }
}
