package com.example.jpaboard.config;

import com.example.jpaboard.member.domain.Member;
import jakarta.annotation.Resource;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
@EnableJpaAuditing
@Configuration
public class AuditorAwareConfig {

    private final Member memberInfo;

    @Autowired
    public AuditorAwareConfig(Member memberInfo) {
        this.memberInfo = memberInfo;
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAware<>() {

            @Override
            public Optional<Long> getCurrentAuditor() {
                Long userId = memberInfo.getId();
                return Optional.of(userId);
            }

        };
    }

}

 */
