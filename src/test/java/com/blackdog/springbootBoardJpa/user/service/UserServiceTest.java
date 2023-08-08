package com.blackdog.springbootBoardJpa.user.service;

import com.blackdog.springbootBoardJpa.domain.user.model.User;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Age;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Name;
import com.blackdog.springbootBoardJpa.domain.user.repository.UserRepository;
import com.blackdog.springbootBoardJpa.domain.user.service.UserService;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserCreateRequest;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponse;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponses;
import com.blackdog.springbootBoardJpa.global.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.blackdog.springbootBoardJpa.global.response.ErrorCode.NOT_FOUND_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name(new Name("park"))
                .age(new Age(12))
                .hobby("야구")
                .build();
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void saveUser_Dto_ReturnUserResponse() {
        //given
        UserCreateRequest request = new UserCreateRequest(
                new Name("park"),
                new Age(26),
                "축구"
        );

        //when
        UserResponse response = userService.saveUser(request);

        //then
        User savedUser = userRepository.findById(response.id()).get();
        assertThat(savedUser.getId()).isEqualTo(savedUser.getId());
    }

    @Test
    @DisplayName("ID를 이용해 유저를 삭제한다.")
    void deleteUserById_Id_Success() {
        //given
        User savedUser = userRepository.save(user);

        //when
        userService.deleteUserById(savedUser.getId());

        //then
        Optional<User> optionalUser = userRepository.findById(savedUser.getId());
        assertThat(optionalUser).isEmpty();
    }

    @Test
    @DisplayName("ID를 이용하여 특정 유저를 조회한다.")
    void findUserById_Id_ReturnUserResponse() {
        //given
        User savedUser = userRepository.save(user);

        //when
        UserResponse userResponse = userService.findUserById(savedUser.getId());

        //then
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.age()).isEqualTo(savedUser.getAge().getAgeValue());
        assertThat(userResponse.name()).isEqualTo(savedUser.getName().getNameValue());
        assertThat(userResponse.hobby()).isEqualTo(savedUser.getHobby());
    }

    @Test
    @DisplayName("유효하지 않은 Id로 단건 조회시 조회에 실패한다.")
    void findUserById_Id_ThrowUserNotFoundException() {
        //given
        User savedUser = userRepository.save(user);

        //when & then
        assertThatThrownBy(() -> userService.findUserById(1000L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(NOT_FOUND_USER.getMessage());
    }


    @ParameterizedTest
    @DisplayName("페이징을 이용하여 유저를 전체 조회한다.")
    @MethodSource("user_Data")
    void findAllUsers_Pageable_UserResponses(List<User> users) {
        //given
        users.forEach(user -> userRepository.save(user));

        //when
        UserResponses responses = userService.findAllUsers(PageRequest.of(0, 2));

        //then
        assertThat(responses).isNotNull();
        assertThat(responses.userResponses()).isNotEmpty();
        assertThat(responses.userResponses().getSize()).isEqualTo(2);
    }

    private static Stream<List<User>> user_Data() {
        User user1 = User.builder()
                .name(new Name("Park"))
                .age(new Age(12))
                .hobby("야구")
                .build();
        User user2 = User.builder()
                .name(new Name("Kim"))
                .age(new Age(21))
                .hobby("농구")
                .build();
        return Stream.of(List.of(user1, user2));
    }

}
