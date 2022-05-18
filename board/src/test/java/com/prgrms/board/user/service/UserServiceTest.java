package com.prgrms.board.user.service;

import com.prgrms.board.common.exception.NotFoundException;
import com.prgrms.board.user.dto.UserRequest;
import com.prgrms.board.user.dto.UserResponse;
import com.prgrms.board.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    UserResponse user;

    @BeforeEach
    void setUp() {
        user = userService.insert(new UserRequest("wansu", 27L, "soccer"));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void user_저장() {
        Assertions.assertThat(userService.findOne(user.getUserId())).isNotNull();
    }

    @Test
    void 없는_user_조회() {
        Assertions.assertThatThrownBy(() -> userService.findOne(2L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 같은_취미를_가진_user_조회() {
        Pageable pageable = PageRequest.of(0, 10);
        Slice<UserResponse> all = userService.findAllByHobby("soccer", pageable);
        Assertions.assertThat(all.getContent().size()).isNotZero();
    }

    @Test
    void user_삭제() {
        userService.deleteById(user.getUserId());
        Assertions.assertThatThrownBy(() -> userService.findOne(user.getUserId()))
                .isInstanceOf(NotFoundException.class);
    }
}