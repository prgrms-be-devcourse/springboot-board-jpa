package dev.jpaboard.user.application;

import dev.jpaboard.post.repository.PostRepository;
import dev.jpaboard.user.domain.User;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import dev.jpaboard.user.dto.response.UserInfoResponse;
import dev.jpaboard.user.exception.UserNotFoundException;
import dev.jpaboard.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class UserServiceTest {

    private static final String 이메일 = "qkrdmswl1018@naver.com";
    private static final String 비밀번호 = "QWert123?";
    private static final String 이름 = "name";
    private static final int 나이 = 23;
    private static final String 취미 = "hobby";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User user;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        user = savedUser();
    }

    private User savedUser() {
        User user = User.builder()
                .email(이메일)
                .password(비밀번호)
                .name(이름)
                .age(나이)
                .hobby(취미)
                .build();

        return userRepository.save(user);
    }

    @DisplayName("존재하는 유저 아이디를 입력할 경우 유저를 수정할 수 있다.")
    @Test
    void updateUserTest() {
        String 수정된_이름 = "원건희";
        String 수정된_취미 = "수정된 취미";

        Long id = user.getId();

        userService.update(new UserUpdateRequest(수정된_이름, 수정된_취미), id);

        User findUser = userRepository.findById(id).get();
        assertAll(
                () -> assertThat(findUser.getName()).isEqualTo(수정된_이름),
                () -> assertThat(findUser.getHobby()).isEqualTo(수정된_취미)
        );
    }

    @DisplayName("존재하는 유저 아이디를 입력할 경우 유저를 조회할 수 있다.")
    @Test
    void findUserTest() {
        Long id = user.getId();

        UserInfoResponse findUser = userService.findUser(id);

        assertAll(
                () -> assertThat(findUser.email()).isEqualTo(이메일),
                () -> assertThat(findUser.name()).isEqualTo(이름),
                () -> assertThat(findUser.age()).isEqualTo(나이),
                () -> assertThat(findUser.hobby()).isEqualTo(취미)
        );
    }

    @DisplayName("존재하는 유저 아이디를 입력할 경우 유저를 삭제할 수 있다.")
    @Test
    void deleteUserTest() {
        Long id = user.getId();
        userService.delete(id);

        Optional<User> findUser = userRepository.findById(id);

        assertFalse(findUser.isPresent());
    }

    @DisplayName("존재하지 않는 유저 아이디를 입력할 경우 유저를 수정할 수 없다.")
    @Test
    void updateFailTest() {
        String 수정된_이름 = "원건희";
        String 수정된_취미 = "수정된 취미";

        assertThatThrownBy(() -> userService.update(new UserUpdateRequest(수정된_이름, 수정된_취미), 102938475L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("존재하지 않는 유저 아이디를 입력할 경우 유저를 조회할 수 없다.")
    @Test
    void findUserFailTest() {
        assertThatThrownBy(() -> userService.findUser(1029384756L))
                .isInstanceOf(UserNotFoundException.class);
    }

}
