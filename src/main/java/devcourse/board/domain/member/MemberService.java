package devcourse.board.domain.member;

import devcourse.board.domain.member.model.MemberRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void save(MemberRequest.JoinDto joinDto) {
        memberRepository.save(MemberRequest.toEntity(joinDto));
    }
}
