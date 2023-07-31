package kr.co.boardmission.member.application;

import kr.co.boardmission.member.domain.Member;
import kr.co.boardmission.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;

    public void createMember(String name) {
        memberRepository.save(new Member(name));
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("멤버 없다 크크루삥뽕"));
    }
}
