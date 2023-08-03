package com.jpaboard.domain.user.application;

import com.jpaboard.domain.user.dto.request.UserCreationRequest;
import com.jpaboard.domain.user.dto.request.UserUpdateRequest;
import com.jpaboard.domain.user.dto.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(UserServiceImpl.class)
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    private Long userId;

    @BeforeEach
    void setup() {
        userId = userService.createUser(new UserCreationRequest("이름", 20, "취미"));
    }

    @Test
    @DisplayName("유저를 조회할 수 있다.")
    void testFindUserById() {
        // given
        // when
        UserResponse userResponse = userService.findUserById(userId);
        // then
        assertThat(userResponse).isNotNull();
    }

    @Test
    @DisplayName("유저를 수정할 수 있다.")
    void testUpdate() {
        // given
        UserUpdateRequest request = new UserUpdateRequest("수정된 이름", 22, "수정된 취미");
        // when
        userService.updateUser(userId, request);
        UserResponse userResponse = userService.findUserById(userId);
        // then
        assertThat(userResponse).isNotNull()
                .extracting(UserResponse::name, UserResponse::age, UserResponse::hobby)
                .containsExactly(request.name(), request.age(), request.hobby());
    }

    @Test
    @DisplayName("유저를 삭제할 수 있다.")
    void testDelete() {
        // given
        // when
        userService.deleteUser(userId);
        // then
        assertThatThrownBy(() -> userService.findUserById(userId))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
