package devcourse.board.domain.member;

import devcourse.board.domain.member.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Rollback(false)
    @DisplayName("회원 조회")
    void save() {
        // given
        Member member = new Member("홍길동");
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findOne(member.getId());

        // then
        assertThat(findMember).isSameAs(member);
    }
}