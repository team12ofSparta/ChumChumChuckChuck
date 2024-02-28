package com.example.sparta.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    // 메인 클래스에서 @EnableJpaAuditing 을 빼기 위한 Config 클래스
}