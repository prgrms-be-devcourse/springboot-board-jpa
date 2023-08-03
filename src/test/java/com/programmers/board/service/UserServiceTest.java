package com.programmers.board.service;

import com.programmers.board.domain.User;
import com.programmers.board.dto.UserDto;
import com.programmers.board.exception.AuthorizationException;
import com.programmers.board.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    User givenUser;

    @BeforeEach
    void setUp() {
        givenUser = new User("name", 20, "hobby");
    }

    @Nested
    @DisplayName("중첩: 회원 생성")
    class CreateUserTest {
        @Test
        @DisplayName("성공")
        void createUser() {
            //given
            String name = "name";
            int age = 20;
            String hobby = "취미";

            //when
            userService.createUser(name, age, hobby);

            //then
            then(userRepository).should().save(any());
        }

        @ParameterizedTest
        @CsvSource({
                "한글은 안됨", "thisIsOver30CharactersThenException"
        })
        @DisplayName("예외: 잘못된 이름 문자열")
        void createUser_ButInvalidName(String invalidName) {
            //given
            int age = 20;
            String hobby = "hobby";

            //when
            //then
            assertThatThrownBy(() -> userService.createUser(invalidName, age, hobby))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "숫자는 안됨1", "thisIsNotContainsNumberButOverFiftyCharactersThenException"
        })
        @DisplayName("예외: 잘못된 취미 문자열")
        void createUser_ButInvalidHobby(String invalidHobby) {
            //given
            String name = "name";
            int age = 20;

            //when
            //then
            assertThatThrownBy(() -> userService.createUser(name, age, invalidHobby))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("성공: 회원 목록 조회")
    void findUsers() {
        //given
        int page = 0;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        PageImpl<User> givenResult = new PageImpl<>(List.of(givenUser), pageRequest, 1);

        given(userRepository.findAll(any(PageRequest.class))).willReturn(givenResult);

        //when
        Page<UserDto> users = userService.findUsers(page, size);

        //then
        assertThat(users.getContent()).hasSize(1);
    }

    @Nested
    @DisplayName("중첩: 회원 단건 조회")
    class FindUserTest {
        @Test
        @DisplayName("성공")
        void findUser() {
            //given
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));

            //when
            UserDto findUserDto = userService.findUser(1L);

            //then
            assertThat(findUserDto.getName()).isEqualTo(givenUser.getName());
            assertThat(findUserDto.getAge()).isEqualTo(givenUser.getAge());
            assertThat(findUserDto.getHobby()).isEqualTo(givenUser.getHobby());
        }

        @Test
        @DisplayName("예외: 존재하지 않는 회원")
        void findUser_ButEmpty() {
            //given
            given(userRepository.findById(any())).willReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> userService.findUser(1L))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Nested
    @DisplayName("중첩: 회원 수정")
    class UpdateUserTest {
        @Test
        @DisplayName("성공")
        void updateUser() {
            //given
            String updateName = "updateName";
            Integer updateAge = 22;
            String updateHobby = "updateHobby";

            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));

            //when
            userService.updateUser(1L, 1L, updateName, updateAge, updateHobby);

            //then
            assertThat(givenUser.getName()).isEqualTo(updateName);
            assertThat(givenUser.getAge()).isEqualTo(updateAge);
            assertThat(givenUser.getHobby()).isEqualTo(updateHobby);
        }

        @Test
        @DisplayName("예외: 업데이트 대상 회원과 로그인 회원 불일치")
        void updateUser_ButNotEqualUser() {
            //given
            Long userId = 1L;
            Long loginUserId = 2L;

            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));

            //when
            //then
            assertThatThrownBy(() -> userService.updateUser(loginUserId, userId, null, null, null))
                    .isInstanceOf(AuthorizationException.class);
        }
    }

    @Nested
    @DisplayName("중첩: 회원 삭제")
    class DeleteUserTest {
        @Test
        @DisplayName("성공")
        void deleteUser() {
            //given
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));

            //when
            userService.deleteUser(1L, 1L);

            //then
            then(userRepository).should().delete(any());
        }

        @Test
        @DisplayName("예외: 업데이트 대상 회원과 로그인 회원 불일치")
        void deleteUser_ButNotEqualUser() {
            //given
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));

            //when
            //then
            assertThatThrownBy(() -> userService.deleteUser(1L, 2L))
                    .isInstanceOf(AuthorizationException.class);
        }
    }
}