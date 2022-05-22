package com.kdt.board.configuration;

import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // ToDo SpringSecurityContext 에서 session 정보 가져와서 설정
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
