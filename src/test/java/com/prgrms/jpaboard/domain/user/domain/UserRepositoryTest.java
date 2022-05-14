package com.prgrms.jpaboard.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user = User.builder()
            .name("jerry")
            .age(25)
            .hobby("누워 있기")
            .createdBy("jerry")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Test
    @DisplayName("user 생성 테스트")
    void testCreate() {
        User createdUser = userRepository.save(user);
        Optional<User> retUser = userRepository.findById(createdUser.getId());

        assertThat(retUser).isNotEmpty();
    }

    @Test
    @DisplayName("수정 테스트")
    void testUpdate() {
        // when
        User createdUser = userRepository.save(user);

        // given
        User retUser = userRepository.findById(createdUser.getId()).get();
        retUser.updateAge(26);

        // then
        User retUser2 = userRepository.findById(createdUser.getId()).get();
        assertThat(retUser2.getAge()).isEqualTo(26);
        assertThat(retUser2.getUpdatedAt()).isNotEqualTo(retUser2.getCreatedAt());
    }

    @Test
    @DisplayName("삭제 테스트")
    void testDelete() {
        User createdUser = userRepository.save(user);

        userRepository.delete(createdUser);

        Optional<User> retUser = userRepository.findById(createdUser.getId());
        assertThat(retUser).isEmpty();
    }
}