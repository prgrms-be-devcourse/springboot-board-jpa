package devcourse.board.domain.login;

import devcourse.board.domain.login.model.LoginRequest;
import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoginService loginService;

    @Test
    @DisplayName("유효하지 않은 이메일로 로그인 시 예외가 발생한다.")
    void should_throw_exception_for_invalid_email() {
        // given
        Member member = Member.create(
                "example@email.com",
                "0000",
                "member",
                null,
                null);
        memberRepository.save(member);

        LoginRequest loginRequest = new LoginRequest("invalidMail@email.com", "0000");

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(loginRequest);
        });
    }

    @Test
    @DisplayName("유효하지 않은 비밀번호로 로그인 시 예외가 발생한다.")
    void should_throw_exception_for_invalid_password() {
        // given
        Member member = Member.create(
                "example@email.com",
                "0000",
                "member",
                null,
                null);
        memberRepository.save(member);

        LoginRequest loginRequest = new LoginRequest("example@email.com", "9999");

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(loginRequest);
        });
    }
}