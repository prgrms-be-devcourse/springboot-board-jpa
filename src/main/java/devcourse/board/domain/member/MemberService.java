package devcourse.board.domain.member;

import devcourse.board.domain.member.model.Member;
import devcourse.board.domain.member.model.MemberJoinDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(MemberJoinDto memberJoinDto) {
        Member member = memberJoinDto.toEntity();
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Member doesn't exist for memberId={0}", memberId
                )));
    }
}
