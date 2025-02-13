package com.example.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OnboardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnboardingApplication.class, args);
    }

}
