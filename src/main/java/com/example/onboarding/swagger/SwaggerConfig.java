package com.example.onboarding.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        SecurityScheme jwtTokenAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");

        // 쿠키 인증 방식 추가
        SecurityScheme cookieAuth = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("refresh");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Token")
                .addList("CookieAuth");

        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Token", jwtTokenAuth)
                                .addSecuritySchemes("CookieAuth", cookieAuth)
                )
                .addSecurityItem(securityRequirement);
    }
}