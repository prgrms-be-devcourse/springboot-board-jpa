package kr.co.boardmission.member.application;

import kr.co.boardmission.member.domain.Member;
import kr.co.boardmission.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(String name) {
        return memberRepository.save(new Member(name));
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Member"));
    }
}
