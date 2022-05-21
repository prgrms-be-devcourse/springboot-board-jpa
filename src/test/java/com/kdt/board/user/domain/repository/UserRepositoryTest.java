package com.kdt.board.user.domain.repository;

import com.kdt.board.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("save 메서드 테스트")
    public void save() {
        // given
        final User user = new User.Builder()
                .name("test")
                .email("test@test.com")
                .build();

        // when
        final User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser).isNotNull();
    }
}
