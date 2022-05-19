package com.waterfogsw.springbootboardjpa.user.repository;

import com.waterfogsw.springbootboardjpa.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("save 메서드 테스트")
    public void save() {
        // given
        final var user = User.builder()
                .name("test")
                .email("test@naver.com")
                .build();

        // when
        final var saved = userRepository.save(user);

        // then
        assertThat(saved).isNotNull();
    }
}
