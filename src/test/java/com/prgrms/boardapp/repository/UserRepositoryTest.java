package com.prgrms.boardapp.repository;

import com.prgrms.boardapp.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static com.prgrms.boardapp.common.UserCreateUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("정상적인 유저 Entity를 저장할 수 있다.")
    void testSave() {
        User user = createUser();

        User savedUser = userRepository.save(user);
        Optional<User> findUser = userRepository.findById(savedUser.getId());
        assertAll(
                () -> assertThat(findUser).isPresent(),
                () -> assertThat(findUser.get().getId()).isEqualTo(savedUser.getId()),
                () -> assertThat(findUser.get().getCommonEmbeddable().getCreatedAt()).isNotNull()
        );
    }
}