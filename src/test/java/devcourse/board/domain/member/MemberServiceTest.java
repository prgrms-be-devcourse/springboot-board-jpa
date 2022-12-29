package devcourse.board.domain.member;

import devcourse.board.domain.member.model.Member;
import devcourse.board.domain.member.model.MemberJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입")
    void save() {
        // given
        MemberJoinRequest joinRequest = new MemberJoinRequest("example@gmail.com", "0000", "member");

        // when
        memberService.join(joinRequest);

        // then
        List<Member> findAllMember = memberRepository.findAll();
        assertThat(findAllMember).isNotEmpty();
    }
}