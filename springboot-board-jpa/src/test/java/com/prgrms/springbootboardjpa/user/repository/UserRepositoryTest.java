package com.prgrms.springbootboardjpa.user.repository;

import com.prgrms.springbootboardjpa.user.entity.Name;
import com.prgrms.springbootboardjpa.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp(){
        user = new User.UserBuilder()
                .nickName("Nickname")
                .age(20)
                .hobby("Sleep")
                .name(Name.builder()
                        .firstName("Ella")
                        .lastName("Ma")
                        .build())
                .password("Password123")
                .email("test@gmail.com")
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .createdBy(Name.builder()
                        .firstName("Ella")
                        .lastName("Ma")
                        .build().toString())
                .build();

        userRepository.save(user);
    }

    @AfterEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        User foundUser = userRepository.findByEmail(user.getEmail());

        assertThat(foundUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void findByEmailNotExist(){
        User foundUser = userRepository.findByEmail("notExist@gmail.com");

        assertThat(foundUser).isNull();
    }

    @Test
    void findByNickName() {
        User foundUser = userRepository.findByNickName(user.getNickName());

        assertThat(foundUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void findByNickNameNotExist(){
        User foundUser = userRepository.findByNickName("NewNickName");

        assertThat(foundUser).isNull();
    }
}