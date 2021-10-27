package kdt.prgrms.devrun.user.service;

import kdt.prgrms.devrun.common.exception.DuplicatedEmailException;
import kdt.prgrms.devrun.common.exception.DuplicatedLoginIdException;
import kdt.prgrms.devrun.user.dto.AddUserRequestDto;
import kdt.prgrms.devrun.user.dto.DetailUserDto;
import kdt.prgrms.devrun.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("SimpleUserService 단위 테스트")
class SimpleUserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사용자 상세조회 테스트")
    void test_getUserById() {
        // given
        final AddUserRequestDto addUserRequestDto = AddUserRequestDto.builder()
            .name("김지훈")
            .age(27)
            .email("devrunner21@gmail.com")
            .loginId("devrunner21")
            .loginPw("12345")
            .build();
        final Long savedUserId = userService.createUser(addUserRequestDto);

        // when
        final DetailUserDto detailUserDto = userService.getUserById(savedUserId);

        // then
        assertThat(detailUserDto.getUserId(), is(savedUserId));
    }

    @Test
    @DisplayName("회원가입 테스트")
    void test_createUser() {
        // given
        final AddUserRequestDto addUserRequestDto = AddUserRequestDto.builder()
            .name("김지훈")
            .age(27)
            .email("devrunner21@gmail.com")
            .loginId("devrunner21")
            .loginPw("12345")
            .build();

        // when
        final Long savedUserId = userService.createUser(addUserRequestDto);

        // then
        final DetailUserDto detailUserDto = userService.getUserById(savedUserId);
        assertThat(detailUserDto, notNullValue());
        assertThat(detailUserDto.getUserId(), is(savedUserId));
    }

    @Test
    @DisplayName("회원가입 시 중복된 LoginId 입력 테스트")
    void test_createUser_duplicatedLoginIdException() {
        // given
        final AddUserRequestDto addUserRequestDto1 = AddUserRequestDto.builder()
            .name("김지훈")
            .age(27)
            .email("devrunner21@gmail.com")
            .loginId("devrunner21")
            .loginPw("12345")
            .build();
        userService.createUser(addUserRequestDto1);

        final AddUserRequestDto addUserRequestDto2 = AddUserRequestDto.builder()
            .name("김수영")
            .age(28)
            .email("njpyh@gmail.com")
            .loginId(addUserRequestDto1.getLoginId())
            .loginPw("12345")
            .build();

        // when  // then
        assertThatThrownBy(() -> userService.createUser(addUserRequestDto2)).isInstanceOf(DuplicatedLoginIdException.class);
    }

    @Test
    @DisplayName("회원가입 시 중복된 Email 입력 테스트")
    void test_createUser_duplicatedEmailException() {
        // given
        final AddUserRequestDto addUserRequestDto1 = AddUserRequestDto.builder()
            .name("김지훈")
            .age(27)
            .email("devrunner21@gmail.com")
            .loginId("devrunner21")
            .loginPw("12345")
            .build();
        userService.createUser(addUserRequestDto1);

        final AddUserRequestDto addUserRequestDto2 = AddUserRequestDto.builder()
            .name("김수영")
            .age(28)
            .email(addUserRequestDto1.getEmail())
            .loginId("njpyh")
            .loginPw("12345")
            .build();

        // when  // then
        assertThatThrownBy(() -> userService.createUser(addUserRequestDto2)).isInstanceOf(DuplicatedEmailException.class);
    }

}
