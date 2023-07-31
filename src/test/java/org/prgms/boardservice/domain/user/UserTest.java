package org.prgms.boardservice.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.prgms.boardservice.util.ErrorMessage.*;


class UserTest {

    @Test
    @DisplayName("유효하지 않은 형식의 이메일을 넣으면 에러가 발생한다.")
    void should_ThrowException_When_InvalidPatternEmail() {
        String invalidEmail = "hjynaver.com";
        String validPassword = "devcourse4!";
        String validNickname = "hyj";

        assertThatThrownBy(() -> new User(invalidEmail, validPassword, validNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_USER_EMAIL_PATTERN.getMessage());
    }

    @Test
    @DisplayName("유효하지 않은 형식의 비밀번호를 넣으면 에러가 발생한다.")
    void should_ThrowException_When_InvalidPatternPassword() {
        String validEmail = "hjy@naver.com";
        String inValidPassword = "devcourse4";
        String validNickname = "hyj";

        assertThatThrownBy(() -> new User(validEmail, inValidPassword, validNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_USER_PASSWORD_PATTERN.getMessage());
    }

    @Test
    @DisplayName("비밀번호는 암호화된다.")
    void should_EncodedPassword_When_EncodePasswordMethod() {
        String email = "hjy@naver.com";
        String password = "devcourse4!";
        String nickname = "hyj";

        User user = new User(email, password, nickname);

        assertThat(user.getPassword()).isNotEqualTo(password);
    }

    @Test
    @DisplayName("유효하지 않은 길이의 닉네임을 넣으면 에러가 발생한다.")
    void should_ThrownException_When_InvalidLengthNickname() {
        String email = "hjy@naver.com";
        String password = "devcourse4!";
        String nicknameLessThanTwo = "j";
        String nicknameMoreThanTen = "backdoongee";

        assertThatThrownBy(() -> {
            new User(email, password, nicknameLessThanTwo);
            new User(email, password, nicknameMoreThanTen);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_USER_NICKNAME_LENGTH.getMessage());
    }

    @Test
    @DisplayName("유저가 성공적으로 생성된다.")
    void should_Success_When_NewUser() {
        String email = "hjy@naver.com";
        String password = "devcourse4!";
        String nickname = "hyj";

        User user = new User(email, password, nickname);

        assertThat(user).isNotNull();
    }
}
