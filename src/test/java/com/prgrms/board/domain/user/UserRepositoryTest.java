package com.prgrms.board.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("회원 생성 테스트")
    @Test
    void createUser() {
        //given
        User user = User.builder()
                .name("이범진")
                .age(27)
                .hobby("게임")
                .build();

        //when
        userRepository.save(user);

        //then
        User findUser = userRepository.findById(user.getId()).get();
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getAge()).isEqualTo(user.getAge());
        assertThat(findUser.getHobby()).isEqualTo(user.getHobby());
    }

    @DisplayName("회원 조회 테스트")
    @Test
    void selectUser() {
        //given
        User user = User.builder()
                .name("이범진")
                .age(27)
                .hobby("게임")
                .build();

        //when
        userRepository.save(user);

        //then
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(1);
    }

    @DisplayName("회원 수정 테스트")
    @Test
    @Transactional
    void updateUser() {
        //given
        User user = User.builder()
                .name("이범진")
                .age(27)
                .hobby("게임")
                .build();
        userRepository.save(user);

        //when
        User findUser = userRepository.findById(user.getId()).get();
        findUser.changeHobby("눕기");

        //then
        User updateUser = userRepository.findById(user.getId()).get();
        assertThat(updateUser.getHobby()).isEqualTo(findUser.getHobby());
    }

    @DisplayName("회원 삭제 테스트")
    @Test
    void deleteUser() {
        //given
        User user = User.builder()
                .name("이범진")
                .age(27)
                .hobby("게임")
                .build();
        userRepository.delete(user);

        //when
        List<User> userList = userRepository.findAll();

        //then
        assertThat(userList).hasSize(0);
    }
}