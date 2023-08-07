package com.prgrms.boardjpa.User.application;

import com.prgrms.boardjpa.User.domain.UserRepository;
import com.prgrms.boardjpa.User.dto.UserRequest;
import com.prgrms.boardjpa.User.dto.UserResponse;
import com.prgrms.boardjpa.global.exception.BusinessServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = UserRequest.builder()
                .name("hong")
                .age(10)
                .hobby("computer")
                .build();
    }

    @Test
    @DisplayName("유저 생성 성공")
    void createUserSuccessTest() throws Exception {

        //when
        UserResponse userResponse = userService.join(userRequest);

        //then
        assertThat(userResponse.name()).isEqualTo(userRequest.getName());
        assertThat(userResponse.age()).isEqualTo(userRequest.getAge());
    }

    @Test
    @DisplayName("유저 생성 실패 - 중복된 이름")
    void createUserFailTest() throws Exception {

        UserRequest userRequest2 = UserRequest.builder()
                .name("hong")
                .age(20)
                .hobby("soccer")
                .build();
        UserResponse userResponse1 = userService.join(userRequest);

        //when -> //then
        assertThrows(BusinessServiceException.class, () -> userService.join(userRequest2));
    }
}