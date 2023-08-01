package com.juwoong.springbootboardjpa.user.domain.repository;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import com.juwoong.springbootboardjpa.config.JpaConfig;
import com.juwoong.springbootboardjpa.user.domain.User;

@DataJpaTest
@Import(JpaConfig.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("USER 엔티티 저장에 성공한다.")
    void userSaveTest() {
        // given
        User user = User.builder()
            .name("JUWOONG")
            .age(29)
            .hobby("TRAVEL")
            .build();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(user).usingRecursiveComparison().isEqualTo(savedUser);
    }

}