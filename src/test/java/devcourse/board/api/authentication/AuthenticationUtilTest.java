package devcourse.board.api.authentication;

import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class AuthenticationUtilTest {

    @Autowired
    private MemberRepository memberRepository;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private Member dummyMember;

    @BeforeEach
    void setUp() {
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();

        this.dummyMember = Member.create("example@email.com", "0000", "member", null, null);
    }

    @Test
    @DisplayName("로그인한 회원의 식별자를 조회할 수 있다.")
    void getLoggedInMemberId() {
        // given
        Member member = this.dummyMember;
        memberRepository.save(member);

        Cookie memberIdCookie = new Cookie(CookieConst.MEMBER_ID, String.valueOf(member.getId()));
        response.addCookie(memberIdCookie);
        request.setCookies(response.getCookies());

        // when
        Long loggedInMemberId = AuthenticationUtil.getLoggedInMemberId(request);

        // then
        assertThat(loggedInMemberId).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("저장된 쿠키가 없으면 로그인 정보 조회시 예외가 발생한다.")
    void should_throw_exception_when_getLoggedInMemberId_if_cookie_not_exists() {
        assertThrows(IllegalStateException.class, () -> {
            AuthenticationUtil.getLoggedInMemberId(request);
        });
    }
}