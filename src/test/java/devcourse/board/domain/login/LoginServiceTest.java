package devcourse.board.domain.login;

import devcourse.board.domain.login.model.LoginRequest;
import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LoginServiceTest {

    public static final String VALID_EMAIL = "validEmail@email.com";
    public static final String INVALID_EMAIL = "inValidEmail@email.com";

    public static final String VALID_PASSWORD = "validPassword";
    public static final String INVALID_PASSWORD = "inValidPassword";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoginService loginService;

    private Member dummyMember;

    @BeforeEach
    void setUp() {
        this.dummyMember = Member.create(
                VALID_EMAIL,
                VALID_PASSWORD,
                "member",
                null,
                null
        );
    }

    @Test
    @DisplayName("유효하지 않은 이메일로 로그인 시 예외가 발생한다.")
    void should_throw_exception_for_invalid_email() {
        // given
        Member member = this.dummyMember;
        memberRepository.save(member);

        LoginRequest loginRequest = new LoginRequest(INVALID_EMAIL, VALID_PASSWORD);

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(loginRequest);
        });
    }

    @Test
    @DisplayName("유효하지 않은 비밀번호로 로그인 시 예외가 발생한다.")
    void should_throw_exception_for_invalid_password() {
        // given
        Member member = this.dummyMember;
        memberRepository.save(member);

        LoginRequest loginRequest = new LoginRequest(VALID_EMAIL, INVALID_PASSWORD);

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(loginRequest);
        });
    }
}