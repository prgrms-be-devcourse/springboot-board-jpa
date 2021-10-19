package com.poogle.board.repository.user;

import com.poogle.board.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사용자 저장 후 불러오기")
    public void save_user_test() {
        //given
        String name = "name";
        int age = 27;
        String hobby = "content";

        userRepository.save(User.of(name, age, hobby));

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertAll(
                () -> assertThat(user.getName()).isEqualTo(name),
                () -> assertThat(user.getAge()).isEqualTo(age),
                () -> assertThat(user.getHobby()).isEqualTo(hobby)
        );
    }

    @Test
    @DisplayName("BaseTimeEntity 테스트")
    public void base_time_entity_test() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 10, 19, 0, 0, 0);
        userRepository.save(User.of("name", 27, "hobby"));

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        log.info("[*] createdAt: {}", user.getCreatedAt());
        log.info("[*] modifiedAt: {}", user.getModifiedAt());
        assertAll(
                () -> assertThat(user.getCreatedAt()).isAfter(now),
                () -> assertThat(user.getModifiedAt()).isAfter(now)
        );
    }

}
