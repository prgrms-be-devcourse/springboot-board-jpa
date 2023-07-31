package com.programmers.board.repository;

import com.programmers.board.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuditingTest.TestAuditingConfig.class)
public class AuditingTest {
    @Autowired
    UserRepository userRepository;

    User givenUser;

    @EnableJpaAuditing
    @TestConfiguration
    static class TestAuditingConfig {
        @Bean
        public AuditorAware<String> auditorProvider() {
            return () -> java.util.Optional.of("auditor");
        }
    }

    @BeforeEach
    void init() {
        givenUser = new User("name", 20, "hobby");
        userRepository.save(givenUser);
    }

    @Test
    @DisplayName("성공: 생성일 auditing")
    void createdAt() {
        assertThat(givenUser.getCreatedAt()).isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    @DisplayName("성공: 작성자 auditing")
    void createdBy() {
        assertThat(givenUser.getCreatedBy()).isEqualTo("auditor");
    }
}
