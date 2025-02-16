package com.example.onboarding;

import com.example.onboarding.domain.UserRole;
import com.example.onboarding.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secretKey = "dGhpc2lzbXl0ZXN0c2VjcmV0a2V5c2FkZmRhc2ZmYWZkc2E=";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secretKey);
    }

    @Test
    @DisplayName("✅ 액세스 토큰 생성 및 검증 테스트")
    void createAndValidateAccessToken() {
        // given
        String username = "testUser";
        UserRole role = UserRole.USER;

        // when
        String token = jwtUtil.createAccessToken(username, role);
        String extractedToken = jwtUtil.substringToken(token);

        // then
        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(extractedToken)); // 토큰이 유효해야 함
    }

    @Test
    @DisplayName("✅ 리프레시 토큰 생성 및 검증 테스트")
    void createAndValidateRefreshToken() {
        // given
        String username = "testUser";
        UserRole role = UserRole.USER;

        // when
        String token = jwtUtil.createRefreshToken(username, role);

        // then
        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token)); // 리프레시 토큰이 유효해야 함
    }

    @Test
    @DisplayName("✅ 토큰에서 사용자 정보 추출")
    void getClaimsFromToken() {
        // given
        String username = "testUser";
        UserRole role = UserRole.USER;
        String token = jwtUtil.createAccessToken(username, role);
        String extractedToken = jwtUtil.substringToken(token);

        // when
        Claims claims = jwtUtil.getClaimsFromToken(extractedToken);

        // then
        assertEquals(username, claims.getSubject());
        assertEquals(role.name(), claims.get(JwtUtil.AUTHORIZATION_KEY));
    }

    @Test
    @DisplayName("❌ 잘못된 토큰 검증 실패")
    void validateInvalidToken() {
        // given
        String invalidToken = "invalid.token.value";

        // when
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("✅ 요청 헤더에서 JWT 토큰 추출")
    void getJwtFromHeader() {
        // given
        String token = jwtUtil.createAccessToken("testUser", UserRole.USER);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(JwtUtil.AUTHORIZATION_HEADER)).thenReturn(token);

        // when
        String extractedToken = jwtUtil.getJwtFromHeader(request);

        // then
        assertNotNull(extractedToken);
        assertEquals(jwtUtil.substringToken(token), extractedToken);
    }
}
