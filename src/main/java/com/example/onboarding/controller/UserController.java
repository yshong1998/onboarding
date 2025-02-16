package com.example.onboarding.controller;

import com.example.onboarding.dto.LoginRequestDto;
import com.example.onboarding.dto.LoginResponseDto;
import com.example.onboarding.dto.SignupRequestDto;
import com.example.onboarding.dto.SignupResponseDto;
import com.example.onboarding.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiController {

    private final UserService userService;

    @Override
    @PostMapping("/signup")
    public SignupResponseDto signup(
            @RequestBody SignupRequestDto signupRequestDto
    ) {
        return userService.signup(signupRequestDto);
    }

    /**
     * 실제 로그인은 필터에서 처리하도록 설정
     * swagger에서 로그인을 시도할 수 있도록 하기 위한 api
     * 리턴 형태를 통일하기 위해 초기화되지 않은 LoginResponseDto를 리턴으로 선언
     */
    @Override
    @PostMapping("/sign")
    public LoginResponseDto sign(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return new LoginResponseDto();
    }

    @Override
    @GetMapping("/reissue")
    public String reissue(
            @CookieValue(name = "refresh") String refreshToken,
            HttpServletResponse response) {
        userService.reissue(refreshToken, response);
        return "reissue token success ";
    }
}
