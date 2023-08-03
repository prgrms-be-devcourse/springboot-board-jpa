package com.programmers.jpa_board.user.infra;

import com.programmers.jpa_board.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void 저장_성공() {
        //given
        User user = new User("신범철", 26, "헬스");

        //when
        User saved = repository.save(user);

        //then
        assertThat(saved).usingRecursiveComparison().isEqualTo(user);
    }
}
