package com.example.spring_jpa_post.user.repository;

import com.example.spring_jpa_post.user.entity.Hobby;
import com.example.spring_jpa_post.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user;
    @BeforeEach
    void setup(){
        user = User.builder()
                .name("kys")
                .age(27)
                .hobby(Hobby.TV)
                .build();
    }

    @Test
    @DisplayName("유저를 저장할 수 있다.")
    void name() {
        //given
        userRepository.save(user);
        //when
        List<User> all = userRepository.findAll();
        //then
        Assertions.assertThat(all).hasSize(1);
    }
}