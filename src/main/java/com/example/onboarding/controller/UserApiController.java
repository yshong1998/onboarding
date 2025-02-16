package com.example.onboarding.controller;

import com.example.onboarding.dto.LoginRequestDto;
import com.example.onboarding.dto.LoginResponseDto;
import com.example.onboarding.dto.SignupRequestDto;
import com.example.onboarding.dto.SignupResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "로그인 API")
public interface UserApiController {

    @Operation(
            summary = "회원가입",
            description = "사용자가 아이디와 비밀번호로 회원가입을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "401", description = "회원가입 실패"),
                    @ApiResponse
            }
    )
    SignupResponseDto signup(
            @RequestBody SignupRequestDto signupRequestDto
    );

    @Operation(
            summary = "로그인",
            description = "사용자가 아이디와 비밀번호로 로그인합니다. \n Refresh Token은 ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "401", description = "로그인 실패")
            }
    )
    LoginResponseDto sign(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response);

    @Operation(
            summary = "토큰 재발급",
            description = "사용자가 리프레시토큰을 이용해 토큰을 재발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
                    @ApiResponse(responseCode = "401", description = "토큰 재발급 실패")
            }
    )
    String reissue(
            @CookieValue(name = "refresh") String refreshToken,
            HttpServletResponse response);
}